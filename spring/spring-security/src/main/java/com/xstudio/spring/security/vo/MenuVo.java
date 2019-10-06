package com.xstudio.spring.security.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuVo implements Serializable {

    private static final long serialVersionUID = 7848691639595817979L;
    /**
     * 条目ID
     */
    private String menuId;

    /**
     * 根，默认为0
     */
    private String rootId;

    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 全路径, "-"作为分割
     */
    private String fullPath;

    /**
     * 深度
     */
    private Integer grade;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * URL地址
     */
    private String menuUrl;

    /**
     * 权限
     */
    private String perms;

    /**
     * 排序(用于自定义排序）
     */
    private Integer sortCode;

    /**
     * 单类型： 菜单:menu 内部链接:link
     */
    private String menuType;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单描述
     */
    private String description;

    private Boolean isLeaf = false;

    private List<MenuVo> children;
}
