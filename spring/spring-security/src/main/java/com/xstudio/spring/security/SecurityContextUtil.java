package com.xstudio.spring.security;

import com.xstudio.spring.core.ContextUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author xiaobiao
 * @version 2019/6/4
 */
public class SecurityContextUtil extends ContextUtil {
    /**
     * 获取当前访问的用户
     *
     * @return AppUserDetails
     */
    public static AppUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null == auth) {
            return null;
        }
        if (auth.getPrincipal() instanceof AppUserDetails) {
            return (AppUserDetails) auth.getPrincipal();
        } else {
            return null;
        }
    }

    /**
     * 是否是超级管理员
     *
     * @return 结果
     */
    public static boolean isSuperadmin() {
        return hasRole(getCurrentUser(), "superadmin");
    }

    /**
     * 是否具备**角色
     *
     * @param user     用户信息
     * @param roleName 角色名称
     * @return
     */
    public static boolean hasRole(AppUserDetails user, String roleName) {
        if (null == user || null == user.getAuthorities()) {
            return false;
        }
        String role = "ROLE_" + roleName;

        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            if (role.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
