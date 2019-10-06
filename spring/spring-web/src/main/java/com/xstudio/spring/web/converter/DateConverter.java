package com.xstudio.spring.web.converter;

import com.xstudio.tool.utils.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * 解决： Failed to convert value of type [java.lang.String] to required type [java.util.Date];
 *
 * @author xiaobiao
 * @version 2019/5/17
 */
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        if (source.isEmpty()) {
            return null;
        }

        return DateUtil.parseTime(source);
    }
}
