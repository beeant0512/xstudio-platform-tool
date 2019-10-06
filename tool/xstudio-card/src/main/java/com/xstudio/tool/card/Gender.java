package com.xstudio.tool.card;

/**
 * @author xiaobioa
 * @version 2018/6/28
 */
public enum Gender {
    /**
     * 男
     */
    M("男"),
    /**
     * 女
     */
    F("女"),
    /**
     * 未知
     */
    N("未知");

    private String zhString;

    Gender(String zhString) {
        this.zhString = zhString;
    }

    public String getZhString() {
        return zhString;
    }
}
