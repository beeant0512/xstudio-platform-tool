package com.xstudio.tool.crypto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 文件加密工具
 *
 * @author xiaobiao
 * @version 2017/11/30
 */
public class ProtocolByteUtils {

    private static Logger logger = LogManager.getLogger(ProtocolByteUtils.class);

    private static int bufferSize = 1024;


    public static String getEncrypytByFile(byte[] data, EnCryptType encryptType) {
        String value = null;
        try {
            MessageDigest md = MessageDigest.getInstance(encryptType.getType());
            md.update(data);
            byte[] encrypt = md.digest();
            BigInteger bi = new BigInteger(1, encrypt);
            value = bi.toString(16);
            value = fixEncrypt(encryptType, value);
        } catch (Exception e) {
            logger.error("获取文件加密(SHA256/MD5)", e);
        }
        return value;
    }

    /**
     * 文件加密
     *
     * @param fullfile    文件路径
     * @param encryptType 加密方式
     * @return
     */
    public static String getEncrypytByFile(String fullfile, EnCryptType encryptType) {
        File file = new File(fullfile);
        return getEncrypytByFile(file, encryptType);
    }

    /**
     * 文件加密
     *
     * @param file        文件
     * @param encryptType 加密类型
     * @return
     */
    public static String getEncrypytByFile(File file, EnCryptType encryptType) {
        String value = null;
        try (FileInputStream in = new FileInputStream(file)) {
            return getEncrypytByStream(in, encryptType);
        } catch (Exception e) {
            logger.error("获取文件加密(SHA256/MD5)", e);
        }
        return value;
    }

    /**
     * MD5首位补0处理
     *
     * @param encryptType 加密类型
     * @param value       已计算结果
     * @return 补0后的结果
     */
    private static String fixEncrypt(EnCryptType encryptType, String value) {
        StringBuilder sb = new StringBuilder();
        if (encryptType.getType().equals(EnCryptType.SHA256.getType()) && value.length() < 64) {
            int addNum = 64 - value.length();
            for (int i = 0; i < addNum; i++) {
                sb.append("0");
            }
            sb.append(value);
        } else if (encryptType.getType().equals(EnCryptType.MD5.getType()) && value.length() < 32) {
            int addNum = 32 - value.length();
            for (int i = 0; i < addNum; i++) {
                sb.append("0");
            }
            sb.append(value);
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 获取文件加密
     *
     * @param inputStream 文件流
     * @param encryptType 加密类型
     * @return
     */
    public static String getEncrypytByStream(InputStream inputStream, EnCryptType encryptType) {
        String value = null;
        byte[] buffer = new byte[bufferSize];
        int len;
        try {
            MessageDigest md = MessageDigest.getInstance(encryptType.getType());
            while ((len = inputStream.read(buffer, 0, bufferSize)) != -1) {
                md.update(buffer, 0, len);
            }
            byte[] encrypt = md.digest();
            BigInteger bi = new BigInteger(1, encrypt);
            value = bi.toString(16);
            value = fixEncrypt(encryptType, value);
        } catch (Exception e) {
            logger.error("获取文件加密(SHA256/MD5)", e);
        }
        return value;
    }
}
