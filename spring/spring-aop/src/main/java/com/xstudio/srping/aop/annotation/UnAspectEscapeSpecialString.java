package com.xstudio.srping.aop.annotation;

import java.lang.annotation.*;

/**
 * 反解析特字符串
 *
 * @author xiaobiao
 * @version 1
 * @date 2017/10/20
 */

@Target({ElementType.PARAMETER, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnAspectEscapeSpecialString {
    /**
     * 无需被转义的字段集
     *
     * @return String[]
     */
    String[] fields();
}
