package com.xstudio.tool.exception;

/**
 * token过期
 *
 * @author xiaobiao
 * @version 2019/5/13
 */
public final class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}