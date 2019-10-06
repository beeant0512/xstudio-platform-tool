package com.xstudio.spring.security.handler;

import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.request.RequestUtil;
import com.xstudio.tool.utils.Msg;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用来解决认证过的用户访问无权限资源时的异常
 *
 * @author xiaobiao
 * @version 2017/11/18
 */
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        Msg<Object> msg = new Msg<>(EnError.FORBIDDEN);
        RequestUtil.writeJson(response, msg);
    }
}
