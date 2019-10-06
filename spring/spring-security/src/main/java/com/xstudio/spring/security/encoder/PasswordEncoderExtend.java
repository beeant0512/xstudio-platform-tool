package com.xstudio.spring.security.encoder;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xiaobiao
 * @version 1.0.0 on 2017/6/12.
 */
public interface PasswordEncoderExtend extends PasswordEncoder {
    /**
     * 是否匹配
     *
     * @param userDetails                         用户详情
     * @param usernamePasswordAuthenticationToken 密码
     * @return 是否密码一致
     */
    boolean matches(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken);

    /**
     * 是否匹配
     *
     * @param userDetails 用户详情
     * @param rawPassword 密码
     * @return 是否密码一致
     */
    boolean matches(UserDetails userDetails, String rawPassword);
}
