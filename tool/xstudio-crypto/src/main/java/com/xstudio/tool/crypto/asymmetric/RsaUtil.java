package com.xstudio.tool.crypto.asymmetric;

import com.xstudio.tool.crypto.Base64Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 该算法于1977年由美国麻省理工学院MIT(Massachusetts Institute of Technology)的Ronal Rivest，Adi Shamir和Len Adleman三位年轻教授提出，并以三人的姓氏Rivest，Shamir和Adlernan命名为RSA算法，是一个支持变长密钥的公共密钥算法，需要加密的文件快的长度也是可变的! <br />
 * 所谓RSA加密算法，是世界上第一个非对称加密算法，也是数论的第一个实际应用。它的算法如下： <br />
 * 1.找两个非常大的质数p和q（通常p和q都有155十进制位或都有512十进制位）并计算n=pq，k=(p-1)(q-1)。 <br />
 * 2.将明文编码成整数M，保证M不小于0但是小于n。 <br />
 * 3.任取一个整数e，保证e和k互质，而且e不小于0但是小于k。加密钥匙（称作公钥）是(e, n)。 <br />
 * 4.找到一个整数d，使得ed除以k的余数是1（只要e和n满足上面条件，d肯定存在）。解密钥匙（称作密钥）是(d, n)。 <br />
 * 加密过程： 加密后的编码C等于M的e次方除以n所得的余数。 <br />
 * 解密过程： 解密后的编码N等于C的d次方除以n所得的余数。 <br />
 * 只要e、d和n满足上面给定的条件。M等于N。 <br />
 *
 * @author xiaobiao
 * @date 2018/4/21
 */
public class RsaUtil {
    private static final String TRANSLATION_ALGORITHM = "RSA/ECB/PKCS1Padding";
    /**
     * 默认的RSA算法
     */
    private static final String ALGORITHM = AsymmetricAlgorithm.RSA.getValue();
    private static final String CHAR_ENCODING = "UTF-8";
    /**
     * 日志对象
     */
    private static final Logger logger = LogManager.getLogger(RsaUtil.class);
    /**
     * 指定key的大小
     */
    private static int KEYSIZE = 1024;


    /**
     * 生成密钥对
     */
    public static RsaKey generateKeyPair() throws Exception {
        /* RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /* 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        /* 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /* 生成密匙对 */
        KeyPair kp = kpg.generateKeyPair();
        /* 得到公钥 */
        Key publicKey = kp.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
        String pub = new String(Base64Util.encode(publicKeyBytes), CHAR_ENCODING);
        /* 得到私钥 */
        Key privateKey = kp.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        String pri = new String(Base64Util.encode(privateKeyBytes), CHAR_ENCODING);

        RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
        BigInteger bint = rsp.getModulus();
        byte[] b = bint.toByteArray();
        byte[] deBase64Value = Base64.getEncoder().encode(b);
        String retValue = new String(deBase64Value);

        return new RsaKey(pub, pri, retValue);
    }

    /**
     * 加密
     *
     * @param text      待加密字符串
     * @param publicKey RSA公钥
     * @return null 表示加密失败
     */
    public static String encrypt(String text, String publicKey) {
        Key key = null;
        try {
            key = getPublicKey(publicKey);
            /* 得到Cipher对象来实现对源数据的RSA加密 */
            Cipher cipher = Cipher.getInstance(TRANSLATION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] b = text.getBytes();
            /* 执行加密操作 */
            byte[] b1 = cipher.doFinal(b);
            return new String(Base64Util.decode(b1), CHAR_ENCODING);
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        } catch (InvalidKeyException e) {
            logger.error("", e);
        } catch (NoSuchPaddingException e) {
            logger.error("", e);
        } catch (BadPaddingException e) {
            logger.error("", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        } catch (InvalidKeySpecException e) {
            logger.error("", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 解密算法
     *
     * @param cryptograph 密文
     * @param privateKey  私钥
     * @return null表示解密失败
     */
    public static String decrypt(String cryptograph, String privateKey) {
        try {
            Key key = getPrivateKey(privateKey);
            /* 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
            Cipher cipher = Cipher.getInstance(TRANSLATION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] b1 = Base64Util.decode(cryptograph.getBytes());
            /* 执行解密操作 */
            byte[] b = cipher.doFinal(b1);
            return new String(b);
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        } catch (InvalidKeyException e) {
            logger.error("", e);
        } catch (NoSuchPaddingException e) {
            logger.error("", e);
        } catch (BadPaddingException e) {
            logger.error("", e);
        } catch (InvalidKeySpecException e) {
            logger.error("", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws NoSuchAlgorithmException {@link NoSuchAlgorithmException}
     * @throws InvalidKeySpecException  {@link InvalidKeySpecException}
     */
    public static PublicKey getPublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64Util.decode(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws NoSuchAlgorithmException {@link NoSuchAlgorithmException}
     * @throws InvalidKeySpecException  {@link InvalidKeySpecException}
     */
    public static PrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64Util.decode(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }
}
