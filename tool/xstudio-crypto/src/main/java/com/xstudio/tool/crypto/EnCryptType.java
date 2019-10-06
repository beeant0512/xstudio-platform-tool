package com.xstudio.tool.crypto;

/**
 * 文件加密类型枚举
 *
 * @author xiaobiao
 * @version 2017/11/30
 */
public enum EnCryptType {
    MD5("MD5"),
    SHA256("SHA-256");

    private String type;

    private EnCryptType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
