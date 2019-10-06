package com.xstudio.srping.aop;

import com.alibaba.fastjson.JSON;
import com.xstudio.srping.aop.annotation.UnAspectEscapeSpecialString;
import com.xstudio.tool.utils.JavaBeansUtil;
import com.xstudio.tool.utils.JsonpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 特殊字符过滤
 * <p>
 *
 * @author xiaobiao on 2016/1/15.
 */
@Aspect
@Component
public class AspectEscapeSpecialString implements ThrowsAdvice {
    private static final Logger logger = LogManager.getLogger(AspectEscapeSpecialString.class);

    /**
     * 转义字符切面
     */
    @Pointcut("@annotation(com.xstudio.srping.aop.annotation.EscapeSpecialString)")
    public void escapeSpecialStringPointcut() {
        // 定义切面annotation注解方法
    }

    /**
     * 环绕通知
      */
    @Before("execution(* com.xstudio.*.*Service*.*insert*(..)) "
            + "|| execution(* com.xstudio.*.*Service*.*Insert*(..)) "
            + "|| execution(* com.xstudio.*.*Service*.*update*(..)) "
            + "|| execution(* com.xstudio.*.*Service*.*Update*(..)) "
            + "|| escapeSpecialStringPointcut()")
    public void doAround(JoinPoint joinPoint) {
        if (logger.isTraceEnabled()) {
            logger.trace(JSON.toJSONString(joinPoint));
        }
        Object[] arg = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();

        List<String> unEscapeFeilds = new ArrayList<>();
        if (parameterAnnotations != null && parameterAnnotations.length > 0) {
            unEscapeFeilds = getUnEscapeFields(parameterAnnotations, unEscapeFeilds);
        }
        for (Object obj : arg) {
            if (obj == null) {
                continue;
            }
            Field[] fields = obj.getClass().getDeclaredFields();
            escapeFields(unEscapeFeilds, obj, fields);
        }
    }

    private void escapeFields(List<String> unEscapeFeilds, Object obj, Field[] fields) {
        for (Field field : fields) {
            if (field.getType().isAssignableFrom(String.class)) {
                String fieldName = field.getName();
                if (unEscapeFeilds.contains(fieldName)) {
                    continue;
                }

                Object value = JavaBeansUtil.getter(obj, fieldName);
                /*
                 * enum 或 null 不进行字符转义
                 */
                if (!(value instanceof Enum) && null != value) {
                    value = JsonpUtil.clean((String) value);
                    doFieldValueSet(obj, fieldName, value);
                }
            }
        }
    }

    private List<String> getUnEscapeFields(Annotation[][] parameterAnnotations, List<String> unEscapeFeilds) {
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof UnAspectEscapeSpecialString) {
                    unEscapeFeilds = Arrays.asList(((UnAspectEscapeSpecialString) annotation).fields());
                }
            }
        }
        return unEscapeFeilds;
    }

    private void doFieldValueSet(Object obj, String fieldName, Object value) {
        try {
            JavaBeansUtil.setter(obj, fieldName, value);
        } catch (NoSuchMethodException e) {
            logger.error("{} setter方法NoSuchMethodException", fieldName);
        } catch (InvocationTargetException e) {
            logger.error("{} setter方法InvocationTargetException", fieldName);
        } catch (IllegalAccessException e) {
            logger.error("{} setter方法IllegalAccessException", fieldName);
        }
    }
}