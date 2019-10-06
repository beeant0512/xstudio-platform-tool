package com.xstudio.tool.card;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 身份证工具类
 *
 * @author xiaobiao
 * @version 2019/3/15
 */
public class IdCardUtil {
    /**
     * 星座
     */
    private static final Constellations[] CONSTELLATIONS = {
            Constellations.CAPRICORN,
            Constellations.AQUARIUS,
            Constellations.PISCES,
            Constellations.ARIES,
            Constellations.TAURUS,
            Constellations.GEMINI,
            Constellations.CANCER,
            Constellations.LEO,
            Constellations.VIRGO,
            Constellations.LIBRA,
            Constellations.SCORPIO,
            Constellations.SAGITTARIUS,
            Constellations.CAPRICORN,
    };
    /**
     * 星座的开始日期
     */
    private static final int[] CONSTELLATION_EDGE_DAY = {20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    /**
     * 日志工具
     */
    private static final Logger logger = LogManager.getLogger(IdCardUtil.class);
    /**
     * 一代身份证位数
     */
    private static final Integer SHENG_FEN_ZHENG_1 = 15;
    /**
     * 二代身份证位数
     */
    private static final Integer SHENG_FEN_ZHENG_2_LENGTH = 18;
    /**
     * 生肖
     */
    private static final Zodiac[] ZODIACS = {
            // 鼠
            Zodiac.RAT,
            // 牛
            Zodiac.OX,
            // 虎
            Zodiac.TIGER,
            // 兔
            Zodiac.RABBIT,
            // 龙
            Zodiac.DRAGON,
            // 蛇
            Zodiac.SNAKE,
            // 马
            Zodiac.HORSE,
            // 羊
            Zodiac.SHEEP,
            // 猴
            Zodiac.MONKEY,
            // 鸡
            Zodiac.ROOSTER,
            // 狗
            Zodiac.DOG,
            // 猪
            Zodiac.PIG,
    };
    /**
     * 身份证 每位加权因子
     */
    private static int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    private IdCardUtil() {
    }

    /**
     * <p>
     * 判断18位身份证的合法性
     * </p>
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * <p>
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
     * </p>
     * <p>
     * 1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码；
     * 4.第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码；
     * 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
     * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
     * </p>
     * <p>
     * 第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4
     * 2 1 6 3 7 9 10 5 8 4 2
     * </p>
     * <p>
     * 2.将这17位数字和系数相乘的结果相加。
     * </p>
     * <p>
     * 3.用加出来和除以11，看余数是多少？
     * </p>
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3
     * 2。
     * <p>
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     * </p>
     *
     * @param idcard 身份证
     * @return 是否是有效身份证
     */
    public static boolean isValidateIdcard(String idcard) {
        if (!isIdCardNumber(idcard)) {
            return false;
        }
        // 非18位为假
        if (idcard.length() == SHENG_FEN_ZHENG_1) {
            return false;
        }
        // 获取前17位
        String idcard17 = idcard.substring(0, 17);
        // 获取第18位
        String idcard18Code = idcard.substring(17, 18);
        // 是否都为数字
        char[] c = idcard17.toCharArray();

        int[] bit = convertCharToInt(c);
        int sum17 = 0;
        sum17 = getPowerSum(bit);

        // 将和值与11取模得到余数进行校验码判断
        String checkCode = getCheckCodeBySum(sum17);
        if (null == checkCode) {
            return false;
        }
        // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
        return idcard18Code.equalsIgnoreCase(checkCode);
    }

    /**
     * 将字符数组转为整型数组
     *
     * @param c 字符
     * @return int[]
     */
    private static int[] convertCharToInt(char[] c) {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param bit bit
     * @return int
     */
    private static int getPowerSum(int[] bit) {
        int sum = 0;

        if (power.length != bit.length) {
            return sum;
        }

        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < power.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * power[j];
                }
            }
        }
        return sum;
    }

    /**
     * 将和值与11取模得到余数进行校验码判断
     *
     * @param sum17 sum17
     * @return 校验位
     */
    private static String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "x";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
            default:
        }
        return checkCode;
    }

    /**
     * 数字验证
     *
     * @param cardValue
     * @return 是否是身份数字
     */
    private static boolean isIdCardNumber(String cardValue) {
        if (StringUtils.isEmpty(cardValue)) {
            return false;
        }
        if (cardValue.length() == SHENG_FEN_ZHENG_2_LENGTH && cardValue.toUpperCase().endsWith("X")) {
            cardValue = cardValue.substring(0, cardValue.length() - 1);
        }
        return !"".equals(cardValue) && cardValue.matches("^[0-9]*$");
    }

    /**
     * 获取证件信息
     *
     * @param value
     * @return CardInfo
     */
    public static CardInfo getCardInfo(String value) {
        if (value.length() != SHENG_FEN_ZHENG_2_LENGTH) {
            return null;
        }

        Calendar birthday = getBirthday(value);

        if (null == birthday) {
            return null;
        }

        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        CardInfo cardInfo = new CardInfo();
        // 生日
        cardInfo.setBirthday(birthday.getTime());
        int year = birthday.get(Calendar.YEAR);
        int month = birthday.get(Calendar.MONTH) + 1;
        int day = birthday.get(Calendar.DAY_OF_MONTH);
        String genderNum = value.substring(16, 17);
        // 计算性别
        if (Integer.parseInt(genderNum) % 2 != 0) {
            // 男
            cardInfo.setGender(Gender.M);
        } else {
            // 女
            cardInfo.setGender(Gender.F);
        }

        // 计算年龄
        cardInfo.setAge(now.get(Calendar.YEAR) - year);

        // 星座
        cardInfo.setConstellation(getConstellations(month, day));
        // 生肖
        cardInfo.setZodiac(getZodiac(year));
        return cardInfo;
    }

    /**
     * 获取星座
     *
     * @param month 出生月
     * @param day   出生日
     * @return Collections
     */
    private static Constellations getConstellations(int month, int day) {
        if (day < CONSTELLATION_EDGE_DAY[month - 1]) {
            return CONSTELLATIONS[month - 1];
        }
        return CONSTELLATIONS[month];
    }

    /**
     * 获取生肖
     *
     * @param year 出生年
     * @return Zodiac
     */
    private static Zodiac getZodiac(int year) {
        return ZODIACS[(year - 1900) % ZODIACS.length];
    }

    /**
     * 获取生日
     *
     * @param value 证件号码
     * @return Calendar
     */
    private static Calendar getBirthday(String value) {
        // 获取出生年月日
        String birthday = value.substring(6, 14);
        try {
            Date birthDate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
            // 证件上的日期
            Calendar cardDay = Calendar.getInstance();
            cardDay.setTime(birthDate);
            return cardDay;
        } catch (Exception e) {
            logger.error("获取证件生日异常", e);
            return null;
        }
    }
}
