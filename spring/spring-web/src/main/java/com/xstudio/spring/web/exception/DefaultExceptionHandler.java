package com.xstudio.spring.web.exception;

import com.alibaba.fastjson.JSON;
import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.request.RequestUtil;
import com.xstudio.tool.utils.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author xiaobiao
 * @version 2017/11/27
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    /**
     * 默认异常
     *
     * @param e 异常
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Msg exceptionHandler(RuntimeException e) {
        logger.error("系统异常", e);
        return new Msg<>(EnError.SERVICE_INVALID.getCode(), "系统服务异常");
    }

    /**
     * 空指针异常
     *
     * @param e 异常
     */
    @ResponseBody
    @ExceptionHandler(UndeclaredThrowableException.class)
    public Msg undeclaredThrowableException(RuntimeException e) {
        logger.error("空指针", e);
        return new Msg<>(EnError.SERVICE_INVALID.getCode(), "系统异常: 空指针");
    }

    /**
     * 请求方法不匹配
     *
     * @return Msg
     */
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Msg<String> HttpRequestMethodNotSupportedException() {
        logger.error("请求方式不匹配");
        return new Msg<>(EnError.SERVICE_INVALID.getCode(), "请求方式不匹配");
    }

    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public Msg<String> fileSizeLimitExceededException(RuntimeException e, HttpServletResponse response) {
        Msg<String> msg = new Msg<>(EnError.SERVICE_INVALID.getCode(), "上传文件过大");
        if (logger.isInfoEnabled()) {
            logger.info(JSON.toJSONString(msg));
        }
        String message = e.getMessage();
        if (message.contains("FileSizeLimitExceededException")) {
            String[] permittedSizeOfs = message.split("permitted size of");
            msg.setMsg("最大允许上传" + permittedSizeOfs[1] + "的文件");
        }
        return msg;
    }
}
