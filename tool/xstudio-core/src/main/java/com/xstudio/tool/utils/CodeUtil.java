package com.xstudio.tool.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 字符串工具类
 *
 * @author xiaobiao
 */
public class CodeUtil {
    /**
     * 定义验证码字符.去除了0、O、I、L等容易混淆的字母
     */
    public static final char ALPHA[] = {'2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    /**
     * 随机数
     */
    protected static final SecureRandom RANDOM = new SecureRandom();
    /**
     * 数字的最大索引，不包括最大值
     */
    public static final int numMaxIndex = 8;
    /**
     * 字符的最小索引，包括最小值
     */
    public static final int charMinIndex = numMaxIndex;
    /**
     * 字符的最大索引，不包括最大值
     */
    public static final int charMaxIndex = ALPHA.length;
    /**
     * 大写字符最小索引
     */
    public static final int upperMinIndex = charMinIndex;
    /**
     * 大写字符最大索引
     */
    public static final int upperMaxIndex = upperMinIndex + 23;
    /**
     * 小写字母最小索引
     */
    public static final int lowerMinIndex = upperMaxIndex;
    /**
     * 小写字母最大索引
     */
    public static final int lowerMaxIndex = charMaxIndex;
    /**
     * 所有字符串
     */
    private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 字母字符串
     */
    private static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 数字字符串
     */
    private static final String NUMBERCHAR = "0123456789";

    /**
     * 大部分敏感词汇在10个以内，直接返回缓存的字符串
     */
    private static String[] starArr = {"*", "**", "***", "****", "*****", "******", "*******", "********", "*********", "**********"};


    private CodeUtil() {
    }


    /**
     * 产生0-num的随机数,不包括num
     *
     * @param num 最大值
     * @return 随机数
     */
    public static int num(int num) {
        return RANDOM.nextInt(num);
    }

    /**
     * 产生两个数之间的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int num(int min, int max) {
        return min + RANDOM.nextInt(max - min);
    }

    /**
     * 返回ALPHA中的随机字符
     *
     * @return 随机字符
     */
    public static char alpha() {
        return ALPHA[num(ALPHA.length)];
    }

    /**
     * 返回ALPHA中第0位到第num位的随机字符
     *
     * @param num 到第几位结束
     * @return 随机字符
     */
    public static char alpha(int num) {
        return ALPHA[num(num)];
    }

    /**
     * 返回ALPHA中第min位到第max位的随机字符
     *
     * @param min 从第几位开始
     * @param max 到第几位结束
     * @return 随机字符
     */
    public static char alpha(int min, int max) {
        return ALPHA[num(min, max)];
    }

    /**
     * 字符串变星
     *
     * @param words    需要变星的字符串
     * @param startPos 开始位
     * @param length   加星的字符串长度， 位数不足自动补位
     */
    public static String star(String words, Integer startPos, Integer length) {
        if (null == words || words.isEmpty()) {
            return words;
        }
        int wordsLength = words.length();
        if (startPos < 1) {
            startPos = 1;
        }
        String prefix = "(\\w{" + startPos + "})";
        String middle = "\\w{" + length + "}";
        String suffix = "(\\w{" + (wordsLength - startPos - length) + "})";
        if (wordsLength < startPos + length) {
            middle = "\\w{" + (wordsLength - startPos) + "}";
            suffix = "()";
        }
        String pattern = prefix + middle + suffix;
        return words.replaceAll(pattern, "$1" + getStarChar(length) + "$2");
    }

    /**
     * 生成n个星号的字符串
     *
     * @param length 字符串长度
     * @return 星星符号字符串
     */
    public static String getStarChar(int length) {
        if (length <= 0) {
            return "";
        }
        //大部分敏感词汇在10个以内，直接返回缓存的字符串
        if (length <= 10) {
            return starArr[length - 1];
        }

        //生成n个星号的字符串
        char[] arr = new char[length];
        for (int i = 0; i < length; i++) {
            arr[i] = '*';
        }
        return new String(arr);
    }

    /**
     * 生成验证码
     *
     * @return
     */
    public static String generateCode() {
        Random random = new Random();
        Double dou = random.nextDouble();
        String str = Double.toString(dou);
        str = str.substring(3, 3 + 6);
        return str;
    }

    /**
     * 获取指定长度的纯数字码
     *
     * @param length 长度
     * @return 数字码
     */
    public static String getNumber(int length) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        StringBuilder buffer = new StringBuilder(NUMBERCHAR);
        int range = buffer.length();
        for (int i = 0; i < length; ++i) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * java生成随机数字和字母组合
     *
     * @param length 生成随机数的长度
     * @return
     */
    public static String getChar(int length) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        StringBuilder buffer = new StringBuilder(LETTERCHAR);
        int range = buffer.length();
        for (int i = 0; i < length; ++i) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * java生成随机数字和字母组合
     *
     * @param length 生成随机数的长度
     * @return
     */
    public static String getCharAndNumber(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (choice + random.nextInt(26)));
            } else {
                // 数字
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    public static String getUUID(int length) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").substring(0, length);
    }

    public static String getNonLineUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num       数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuilder sb = new StringBuilder();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                    + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 每次生成的len位数都不相同
     *
     * @param param
     * @param len   长度
     * @return 定长的数字
     */
    public static int getNotSimple(int[] param, int len) {
        Random rand = new Random();
        for (int i = param.length; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = param[index];
            param[index] = param[i - 1];
            param[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = result * 10 + param[i];
        }
        return result;
    }

    /**
     * 生成时间字符串
     *
     * @param length 附带随机字（字符+数字）符串长度
     * @return String
     */
    public static String getTimeStringPlus(int length) {
        //当前时间戳
        String timeString = String.valueOf(System.currentTimeMillis());
        //加入2位随机数
        if (length > 0) {
            timeString = timeString.concat(getCharAndNumber(length));
        }
        return timeString;
    }
}
