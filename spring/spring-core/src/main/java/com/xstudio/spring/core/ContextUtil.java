package com.xstudio.spring.core;

import org.springframework.context.ApplicationContext;

/**
 * @author xiaobiao
 * @version 2019/5/17
 */
public class ContextUtil {
    /**
     * 应用上下文
     */
    private static ApplicationContext context;

    /**
     * 设置应用上下文
     *
     * @param context
     */
    public static void setContext(ApplicationContext context) {
        ContextUtil.context = context;
    }

    /**
     * 获取bean
     *
     * @param requiredType bean类
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    /**
     * 获取bean
     *
     * @param name bean名称
     * @return
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }

    /**
     * 获取配置
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return context.getEnvironment().getProperty(key);
    }

    /**
     * 获取配置
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        return context.getEnvironment().getProperty(key, defaultValue);
    }

    /**
     * 获取配置
     *
     * @param key
     * @param targetType
     * @param <T>
     * @return
     */
    public static <T> T getProperty(String key, Class<T> targetType) {
        return context.getEnvironment().getProperty(key, targetType);
    }

    /**
     * 获取配置
     *
     * @param key
     * @param targetType
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return context.getEnvironment().getProperty(key, targetType, defaultValue);
    }
}
