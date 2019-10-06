package com.xstudio.tool.crypto.asymmetric;

/**
 * 非对称加密算法类型
 *
 * @author xiaobiao
 * @version 2019/5/16
 */
public enum AsymmetricAlgorithm {
    /**
     * RSA算法
     */
    RSA("RSA"),
    /**
     * RSA算法，此算法用了默认补位方式为RSA/ECB/PKCS1Padding
     */
    RSA_ECB_PKCS1("RSA/ECB/PKCS1Padding"),
    /**
     * RSA算法，此算法用了RSA/None/NoPadding
     */
    RSA_None("RSA/None/NoPadding"),
    /**
     * EC算法
     */
    EC("EC");

    private String value;

    /**
     * 构造
     *
     * @param value 算法字符表示，区分大小写
     */
    private AsymmetricAlgorithm(String value) {
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
