package com.xstudio.spring.security.handler;

import com.xstudio.spring.security.exception.InvalidAuthCodeException;
import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.request.RequestUtil;
import com.xstudio.tool.utils.Msg;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaobiao
 * @version 2017/11/27
 */
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        Msg<String> msg = new Msg<>();
        msg.setResult(EnError.NO_MATCH);
        msg.setMsg("账号或密码错误");
        if (exception instanceof UsernameNotFoundException) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("账号或密码错误");
        } else if (exception instanceof LockedException) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("用户已被锁定");
        } else if (exception instanceof DisabledException) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("用户已被禁用");
        } else if (exception instanceof AccountExpiredException) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("登录已过期");
        } else if (exception instanceof CredentialsExpiredException) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("账号或密码错误");
        } else if (exception instanceof InvalidAuthCodeException) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("验证码错误");
        }
        if (null != exception.getMessage()) {
            msg.setMsg(exception.getMessage());
        }
        RequestUtil.writeJson(response, msg);
    }
}
