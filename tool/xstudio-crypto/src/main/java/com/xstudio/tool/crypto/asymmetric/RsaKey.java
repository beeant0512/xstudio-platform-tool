package com.xstudio.tool.crypto.asymmetric;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @date 2018/4/21
 */
public class RsaKey implements Serializable {
    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 系数，模数
     */
    private String modulus;

    public RsaKey(String publicKey, String privateKey, String modulus) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.modulus = modulus;
    }

    public RsaKey(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Getter for property 'publicKey'.
     *
     * @return Value for property 'publicKey'.
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Setter for property 'publicKey'.
     *
     * @param publicKey Value to set for property 'publicKey'.
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Getter for property 'privateKey'.
     *
     * @return Value for property 'privateKey'.
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * Setter for property 'privateKey'.
     *
     * @param privateKey Value to set for property 'privateKey'.
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * Getter for property 'modulus'.
     *
     * @return Value for property 'modulus'.
     */
    public String getModulus() {
        return modulus;
    }

    /**
     * Setter for property 'modulus'.
     *
     * @param modulus Value to set for property 'modulus'.
     */
    public void setModulus(String modulus) {
        this.modulus = modulus;
    }
}
