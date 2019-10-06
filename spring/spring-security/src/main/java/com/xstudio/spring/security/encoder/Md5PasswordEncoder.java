package com.xstudio.spring.security.encoder;

import com.xstudio.spring.security.AppUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * md5加密器
 *
 * @author xiaobiao
 * @version 1.0.0 on 2017/4/12.
 */
public class Md5PasswordEncoder implements PasswordEncoderExtend {
    /**
     * MD5密码前缀
     */
    private static String PREFIX = "{MD5}";

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword.startsWith("{")) {
            return passwordEncoder.matches(rawPassword, encodedPassword);
        }
        return passwordEncoder.matches(rawPassword, PREFIX + encodedPassword);
    }

    @Override
    public boolean matches(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        String rawPassword = usernamePasswordAuthenticationToken.getCredentials().toString();
        rawPassword = ((AppUserDetails) userDetails).getUserId() + rawPassword;
        return matches(rawPassword, userDetails.getPassword());
    }

    @Override
    public boolean matches(UserDetails userDetails, String rawPassword) {
        AppUserDetails appUserDetails = (AppUserDetails) userDetails;
        rawPassword = appUserDetails.getUserId() + rawPassword;
        return matches(rawPassword, userDetails.getPassword());
    }
}
