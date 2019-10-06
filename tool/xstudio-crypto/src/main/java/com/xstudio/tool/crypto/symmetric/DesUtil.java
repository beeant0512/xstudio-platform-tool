package com.xstudio.tool.crypto.symmetric;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * DES加密 <br />
 * 配合前端使用CryptoJS使用 <br />
 * const key  = CryptoJS.enc.Utf8.parse(key); <br />
 * const encode = CryptoJS.DES.encrypt(data, key, { <br />
 *     mode: CryptoJS.mode.ECB, <br />
 *     padding: CryptoJS.pad.Pkcs7, <br />
 * });<br />
 *
 * @author xiaobiao
 * @date 2018/4/21
 */
public class DesUtil {

    private static final String TranslationAlgorithm = SymmetricAlgorithm.DES_ECB_PKCS5.getValue();

    private static final String Algorithm = SymmetricAlgorithm.DES.getValue();

    private static final String charterName = "UTF-8";

    private static Logger logger = LogManager.getLogger(DesUtil.class);

    public static String encrypt(String data, String key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(TranslationAlgorithm);

            cipher.init(Cipher.ENCRYPT_MODE, generateKey(key));
            byte[] encrypted = cipher.doFinal(data.getBytes(charterName));
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static String decrypt(String data, String key) throws Exception {
        try {
            byte[] encrypted = new BASE64Decoder().decodeBuffer(data);
            Cipher cipher = Cipher.getInstance(TranslationAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(key));
            return new String(cipher.doFinal(encrypted));
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 获得秘密密钥
     *
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     */
    private static SecretKey generateKey(String secretKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
        keyFactory.generateSecret(keySpec);
        return keyFactory.generateSecret(keySpec);
    }
}
