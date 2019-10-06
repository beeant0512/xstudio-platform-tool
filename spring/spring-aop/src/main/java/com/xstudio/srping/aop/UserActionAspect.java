package com.xstudio.srping.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xstudio.spring.core.ContextUtil;
import com.xstudio.srping.aop.annotation.LogUserAction;
import com.xstudio.srping.aop.log.ILogUserActionService;
import com.xstudio.srping.aop.log.Log;
import com.xstudio.tool.request.RequestUtil;
import com.xstudio.tool.service.IAbstractService;
import com.xstudio.tool.utils.Msg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author xiaobiao
 * @date 2018/11/8
 */
@Aspect
@Component
public class UserActionAspect {

    private static final Logger logger = LogManager.getLogger(UserActionAspect.class);

    /**
     * 切面注解
     */
    @Pointcut("@annotation(com.xstudio.srping.aop.annotation.LogUserAction)")
    public void logUserActionPointcut() {
        // 定义切面annotation注解方法
    }

    /**
     * 环绕通知
     */
    @Around("logUserActionPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        if (logger.isTraceEnabled()) {
            logger.trace(JSON.toJSONString(joinPoint));
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 日志服务获取
        LogUserAction annotation = method.getAnnotation(LogUserAction.class);
        if (null == annotation) {
            return joinPoint.proceed();
        }

        // 输入参数
        Object[] arg = joinPoint.getArgs();

        // 请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        ILogUserActionService logUserActionService = (ILogUserActionService) ContextUtil.getBean((annotation).service());

        // 是否删除
        boolean doSelect = annotation.delete();
        if (doSelect) {
            IAbstractService selectService = (IAbstractService) ContextUtil.getBean((annotation).selectService());
            String id = annotation.id();
            JSONObject parse = JSON.parseObject((String) arg[0]);
            Msg msg = selectService.selectByPrimaryKey(parse.get(id));
            arg[0] = msg.getData();
        }

        Date actionTime = new Date();
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;

        // 存储日志
        Log log = new Log();

        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        log.setIp(RequestUtil.getIp(request));
        String header = request.getHeader("User-Agent");
        if (null != header) {
            log.setAgent(header);
        }
        if (null != arg && arg.length == 1) {
            log.setInputs(arg[0]);
        } else {
            log.setInputs(arg);
        }

        log.setExecuteTime(time);
        log.setOutputs(result);
        log.setClassName(className);
        log.setMethodName(methodName);
        log.setActionName(annotation.actionName());
        log.setActionTime(actionTime);
        try {
            logUserActionService.saveLog(log);
        } catch (Exception e) {
            logger.error("日志记录异常", e);
        }

        return result;
    }
}
