package com.xstudio.tool.crypto.symmetric;

/**
 * 非对称加密算法类型
 *
 * @author xiaobiao
 * @version 2019/5/16
 */
public enum SymmetricAlgorithm {
    /**
     * DES算法
     */
    DES("DES")
    /**
     * DES算法，此算法用了默认补位方式为DES/ECB/PKCS5Padding
     */
    , DES_ECB_PKCS5("DES/ECB/PKCS5Padding")
    /**
     * AES算法
     */
    , AES("AES")
    /**
     * AES算法，此算法用了默认补位方式为AES/ECB/PKCS5Padding
     */
    , AES_ECB_PKCS5("AES/ECB/PKCS5Padding");

    private String value;

    /**
     * 构造
     *
     * @param value 算法字符表示，区分大小写
     */
    private SymmetricAlgorithm(String value) {
        this.value = value;
    }

    /**
     * 获取算法字符串表示，区分大小写
     *
     * @return 算法字符串表示
     */
    public String getValue() {
        return this.value;
    }
}
