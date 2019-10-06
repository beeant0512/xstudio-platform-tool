package com.xstudio.tool.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转时间戳序列化工具
 *
 * @author xiaobiao
 * @version 2019/2/13
 */
public class DateToTimeStampSerializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object object) {
        // 字符串的时间戳 直接格式化
        String value = String.valueOf(defaultJSONParser.parse());

        if (null == value) {
            return null;
        }

        String pattern = "yyyy-MM-dd HH:mm:ss";
        if (value.length() == 10) {
            pattern = "yyyy-MM-dd";
        }
        // 字符串的时间戳 直接格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String typeName = type.getTypeName();
        try {
            Date date = simpleDateFormat.parse(value);
            Long ts = date.getTime();
            if ("java.lang.Integer".equals(typeName)) {
                return (T) TypeUtils.castToInt(String.valueOf(ts).substring(0, 10));
            }
            return (T) ts;
        } catch (Exception e) {
            return (T) object;
        }
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
