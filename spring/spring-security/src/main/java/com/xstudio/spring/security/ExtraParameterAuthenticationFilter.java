package com.xstudio.spring.security;

import com.xstudio.spring.security.params.LoginParams;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义登录参数获取
 *
 * @author xiaobiao
 */
public class ExtraParameterAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 用户名参数
     */
    private static String USERNAME_PARAM = "username";

    /**
     * 密码参数
     */
    private static String PASSWORD_PARAM = "password";

    public ExtraParameterAuthenticationFilter(AuthenticationManager authenticationManager, String pattern) {
        super(new AntPathRequestMatcher(pattern, "POST"));
        this.setAuthenticationManager(authenticationManager);
    }

    /**
     * Setter for property 'USERNAME_PARAM'.
     *
     * @param USERNAME_PARAM Value to set for property 'USERNAME_PARAM'.
     */
    public void setUsernameParam(String USERNAME_PARAM) {
        ExtraParameterAuthenticationFilter.USERNAME_PARAM = USERNAME_PARAM;
    }

    /**
     * Setter for property 'PASSWORD_PARAM'.
     *
     * @param PASSWORD_PARAM Value to set for property 'PASSWORD_PARAM'.
     */
    public void setPasswordParam(String PASSWORD_PARAM) {
        ExtraParameterAuthenticationFilter.PASSWORD_PARAM = PASSWORD_PARAM;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        LoginParams loginParams = new LoginParams(request);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginParams.get(USERNAME_PARAM), loginParams.get(PASSWORD_PARAM));

        authRequest.setDetails(loginParams);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
