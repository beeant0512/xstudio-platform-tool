package com.xstudio.spring.security.vo;

import com.xstudio.spring.security.AppUserDetails;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class TokenUserVo implements Serializable {

    private static final long serialVersionUID = 1991537490570246610L;
    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户姓名
     */
    private String userFullname;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 权限
     */
    private List<String> perms;

    /**
     * 菜单列表
     */
    private List<MenuVo> menus;

    public TokenUserVo() {
    }

    public TokenUserVo(UsernamePasswordAuthenticationToken user) {
        AppUserDetails appUserDetails = (AppUserDetails) user.getPrincipal();
        this.userId = appUserDetails.getUserId();
        this.username = appUserDetails.getUsername();
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        List<String> permList = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            permList.add(authority.getAuthority());
        }
        this.perms = permList;
    }

    public TokenUserVo(UsernamePasswordAuthenticationToken user, ArrayList<GrantedAuthority> authorities) {
        AppUserDetails appUserDetails = (AppUserDetails) user.getPrincipal();
        this.userId = appUserDetails.getUserId();
        this.username = appUserDetails.getUsername();

        List<String> permList = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            permList.add(authority.getAuthority());
        }
        TokenUserVo adminUserVo = (TokenUserVo) appUserDetails.getDetails();
        this.userFullname = adminUserVo.getUserFullname();
        this.perms = permList;
    }
}
