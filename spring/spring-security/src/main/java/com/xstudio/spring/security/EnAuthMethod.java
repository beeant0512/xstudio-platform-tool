package com.xstudio.spring.security;

/**
 * @author xiaobiao
 * @version 2017/11/18
 */
public enum EnAuthMethod {
    /**
     * 创建
     */
    create,
    /**
     * 获取
     */
    get,
    /**
     * 更新
     */
    update,
    /**
     * 浏览
     */
    browse,
    /**
     * 删除
     */
    delete,
    /**
     * 查看
     */
    view;

    EnAuthMethod() {
    }
}