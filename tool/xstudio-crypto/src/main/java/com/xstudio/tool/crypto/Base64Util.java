package com.xstudio.tool.crypto;

import java.util.Base64;

/**
 * @author xiaobiao
 * @version 2019/5/16
 */
public class Base64Util {
    /**
     * 加密
     *
     * @param src
     * @return
     */
    public static byte[] encode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getEncoder().encode(src);
    }

    /**
     * 解密
     *
     * @param src
     * @return
     */
    public static byte[] decode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getDecoder().decode(src);
    }
}
