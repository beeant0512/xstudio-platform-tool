package com.xstudio.tool.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.lang.reflect.Type;

/**
 * 身份证序列化
 * 保留前六位和后四位，中间值使用*替代
 * 例如： 123456789012345678 序列化后变成 123456********5678
 *
 * @author xiaobiao
 * @version 2018/1/10
 */
public class IdCardValueSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        String value = (String) object;
        String text = value.replaceAll("(\\d{6})\\d+(\\d{4})", "$1*********$2");
        serializer.write(text);
    }
}
