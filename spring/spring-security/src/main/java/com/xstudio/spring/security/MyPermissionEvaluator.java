package com.xstudio.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author xiaobiao
 * @version 1.0.0 on 2017/6/8.
 */
@Component
public class MyPermissionEvaluator implements PermissionEvaluator {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String defaultPermissionPrefix = "PERM_";
    private String defaultPermissionSeparator = ":";

    @Override
    public boolean hasPermission(Authentication authentication, Object menu, Object target) {
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String authority = grantedAuthority.getAuthority();
            String permission = getPermissionWithDefaultPrefix((String) menu, (String) target);
            if (hasPermission(authority, permission)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        String perm = getPermissionWithDefaultPrefix((String) targetId, targetType, (String) permission);
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String authority = grantedAuthority.getAuthority();
            if (hasPermission(authority, perm)) {
                return true;
            }
        }

        return false;
    }

    private String getPermissionWithDefaultPrefix(String targetId, String targetType, String permission) {
        return defaultPermissionPrefix + targetId + defaultPermissionSeparator + targetType + defaultPermissionSeparator + permission;
    }

    private boolean hasPermission(String authority, String permission) {
        AppUserDetails currentUser = SecurityContextUtil.getCurrentUser();
        if (null != currentUser && SecurityContextUtil.isSuperadmin()) {
            logger.debug("verify user {} permission {} true", currentUser.getUserId(), permission);
            return true;
        }
        boolean result = authority.startsWith(defaultPermissionPrefix) && authority.equals(permission);
        logger.debug("verify user permission {} result {}", permission, result);
        return result;
    }

    private String getPermissionWithDefaultPrefix(String target, String permission) {
        if (target == null) {
            return permission;
        }
        if (defaultPermissionPrefix == null || defaultPermissionPrefix.length() == 0) {
            return target + defaultPermissionSeparator + permission;
        }
        if (target.startsWith(defaultPermissionPrefix)) {
            return target + defaultPermissionSeparator + permission;
        }
        return defaultPermissionPrefix + target + defaultPermissionSeparator + permission;
    }

    /**
     * 权限校验
     *
     * @param service 服务
     * @param method  方法
     * @return
     */
    public boolean checkPermission(String service, EnAuthMethod method) {
        AppUserDetails currentUser = SecurityContextUtil.getCurrentUser();
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();
        if (null != authorities) {
            for (GrantedAuthority authority : authorities) {
                if (hasPermission(authority.getAuthority(), defaultPermissionPrefix + service + ":" + method.name())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 权限校验
     *
     * @param service 服务
     * @param method  方法
     * @return
     */
    public boolean checkPermission(String service, String method) {
        if ("*".equals(service) || "*".equals(method)) {
            return true;
        }

        AppUserDetails currentUser = SecurityContextUtil.getCurrentUser();
        Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();
        if (null != authorities) {
            for (GrantedAuthority authority : authorities) {
                if (hasPermission(authority.getAuthority(), defaultPermissionPrefix + service + ":" + method)) {
                    return true;
                }
            }
        }
        return false;
    }
}
