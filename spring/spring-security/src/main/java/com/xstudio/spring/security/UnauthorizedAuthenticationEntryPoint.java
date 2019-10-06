package com.xstudio.spring.security;

import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.request.RequestUtil;
import com.xstudio.tool.utils.Msg;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用来解决匿名用户访问无权限资源时的异常<br />
 * 区别于 AccessDenyHandler 用来解决认证过的用户访问无权限资源时的异常
 *
 * @author xiaobiao
 * @date 2018/11/29
 */
public class UnauthorizedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        Msg<Object> msg = new Msg<>(EnError.FORBIDDEN);
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        RequestUtil.writeJson(response, msg);
    }
}
