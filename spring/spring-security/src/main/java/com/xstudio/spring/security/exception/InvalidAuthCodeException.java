package com.xstudio.spring.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 错误验证码异常
 *
 * @author xiaobiao
 * @version 1.0.0 on 2017/6/9.
 */
public class InvalidAuthCodeException extends AuthenticationException {

    public InvalidAuthCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidAuthCodeException(String msg) {
        super(msg);
    }
}
