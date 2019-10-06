package com.xstudio.tool.crypto.symmetric;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * AES加密 <br />
 * 配合前端使用CryptoJS使用 <br />
 * const key  = CryptoJS.enc.Utf8.parse(key); <br />
 * const encode = CryptoJS.AES.encrypt(data, key, { <br />
 * mode: CryptoJS.mode.ECB, <br />
 * padding: CryptoJS.pad.Pkcs7, <br />
 * });<br />
 *
 * @author xiaobiao
 * @date 2018/4/18
 */
public class AesUtil {
    private static final String TRANSLATION_ALGORITHM = SymmetricAlgorithm.AES_ECB_PKCS5.getValue();
    private static final String ALGORITHM = SymmetricAlgorithm.AES.getValue();
    private static final String CHARTER_NAME = "UTF-8";
    private static Logger logger = LogManager.getLogger(AesUtil.class);

    private AesUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  秘钥
     * @return
     */
    public static String encrypt(String data, String key) {
        return new BASE64Encoder().encode(byteEncrypt(data, key, TRANSLATION_ALGORITHM));
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  秘钥
     * @return
     */
    public static String encrypt(String data, byte[] key) {
        return new BASE64Encoder().encode(byteEncrypt(data, key, TRANSLATION_ALGORITHM));
    }

    public static byte[] byteEncrypt(String data, String key) {
        return byteEncrypt(data, key, TRANSLATION_ALGORITHM);
    }

    public static byte[] byteEncrypt(String data, byte[] key, String instance) {
        try {
            Cipher cipher = Cipher.getInstance(instance);
            SecretKey keyspec = new SecretKeySpec(key, ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            return cipher.doFinal(data.getBytes(CHARTER_NAME));
        } catch (Exception e) {
            logger.error("AES加密异常", e);
            return null;
        }
    }

    public static byte[] byteEncrypt(String data, String key, String instance) {
        try {
            return byteEncrypt(data, key.getBytes(CHARTER_NAME), instance);
        } catch (UnsupportedEncodingException e) {
            logger.error("AES加密异常", e);
            return null;
        }
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  秘钥
     * @return
     */
    public static String decrypt(String data, String key) {
        try {
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
            Cipher cipher = Cipher.getInstance(TRANSLATION_ALGORITHM);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(CHARTER_NAME), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            return new String(cipher.doFinal(encrypted1));
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
}
