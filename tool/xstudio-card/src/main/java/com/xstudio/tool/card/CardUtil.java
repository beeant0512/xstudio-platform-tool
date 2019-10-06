package com.xstudio.tool.card;


import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;


/**
 * 证件类型工具类
 *
 * @author xiaobiao
 * @version 2018/1/10
 */
public class CardUtil {

    /**
     * 默认构造函数
     */
    private CardUtil() {
    }

    /**
     * 获取证件信息
     *
     * @param type  证件类别
     * @param value 证件信息
     * @return CardInfo
     */
    public static CardInfo getCardInfo(CardType type,String value) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        if (!StringUtils.isEmpty(value)) {
            if (type == CardType.IDCARD) {
                return IdCardUtil.getCardInfo(value);
            } else {
                // todo 其他证件计算
            }
        }

        return null;
    }


}
