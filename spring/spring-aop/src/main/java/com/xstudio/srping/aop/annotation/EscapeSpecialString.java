package com.xstudio.srping.aop.annotation;

import java.lang.annotation.*;

/**
 * 特字符串格式化注解
 *
 * @author xiaobiao
 * @version 1.0.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EscapeSpecialString {
}
