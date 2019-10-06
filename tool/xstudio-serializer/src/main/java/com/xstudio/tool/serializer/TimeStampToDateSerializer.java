package com.xstudio.tool.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳转日期序列化工具
 *
 * @author xiaobiao
 * @version 2019/2/13
 */
public class TimeStampToDateSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        // 字符串的时间戳 直接格式化
        String value = String.valueOf(object);
        // 少于10位，加上毫秒后再进行转换
        if (value.length() == 10) {
            value = value + "000";
        }
        Date date = new Date(new Long(value));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        serializer.write(sdf.format(date));
    }
}
