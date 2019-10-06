package com.xstudio.spring.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 过期验证码异常
 *
 * @author xiaobiao
 * @version 1.0.0 on 2017/6/9.
 */
public class ExpiredAuthCodeException extends AuthenticationException {

    public ExpiredAuthCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ExpiredAuthCodeException(String msg) {
        super(msg);
    }
}
