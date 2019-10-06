package com.xstudio.tool.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.xstudio.tool.utils.CodeUtil;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字替换为 <code>*</code> 号, 只保留前面一位和最后一位<br />
 * 例如：<br />
 * 123456 => 1****6
 *
 * @author xiaobiao
 * @date 2018/10/27
 */
public class NumberToStarsSerializer implements ObjectSerializer {

    /**
     * 提取文案中的数字正则表达式
     */
    private static Pattern pattern = Pattern.compile(".*?(\\d+).*?");

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        String value = (String) object;
        Matcher matcher = pattern.matcher(value);
        Integer length = value.length();
        if (matcher.find()) {
            length = matcher.group(1).length();
        }
        String text = value.replaceAll("(\\d{1})\\d+(\\d{1})", "$1" + CodeUtil.getStarChar(length - 2) + "$2");
        serializer.write(text);
    }
}
