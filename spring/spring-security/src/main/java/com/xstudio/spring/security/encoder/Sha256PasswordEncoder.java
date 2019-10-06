package com.xstudio.spring.security.encoder;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * sha-256 加密器
 *
 * @author xiaobiao
 * @version 1.0.0 on 2017/5/2.
 */
public class Sha256PasswordEncoder implements PasswordEncoderExtend {

    private static String PREFIX = "{SHA-256}";
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, PREFIX + encodedPassword);
    }

    @Override
    public boolean matches(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        String rawPassword = usernamePasswordAuthenticationToken.getCredentials().toString();
        return matches(userDetails, rawPassword);
    }

    @Override
    public boolean matches(UserDetails userDetails, String rawPassword) {
        return matches(rawPassword, PREFIX + userDetails.getPassword());
    }
}
