package com.xstudio.spring.security.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/10/3
 */
@Data
public class UserAuthority implements Serializable {
    /**
     * 权限
     */
    private List<String> perms;

    /**
     * 菜单
     */
    private List<MenuVo> menus;

    /**
     * 权限对象
     */
    @JSONField(serialize = false)
    private ArrayList<GrantedAuthority> authorities = new ArrayList<>();
}
