package com.xstudio.tool.web;

import com.xstudio.tool.crypto.EnCryptType;
import com.xstudio.tool.crypto.ProtocolByteUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author xiaobiao
 * @version 2019/5/16
 */
public class ProtocolByteUtil extends ProtocolByteUtils {

    private static Logger logger = LogManager.getLogger(ProtocolByteUtil.class);

    /**
     * 文件加密
     *
     * @param multipartFile 文件
     * @param encryptType   加密类型
     * @return
     */
    public static String getEncrypytByFile(MultipartFile multipartFile, EnCryptType encryptType) {
        String value = null;
        try {
            InputStream in = multipartFile.getInputStream();
            return getEncrypytByStream(in, encryptType);
        } catch (Exception e) {
            logger.error("", e);
        }

        return value;
    }
}
