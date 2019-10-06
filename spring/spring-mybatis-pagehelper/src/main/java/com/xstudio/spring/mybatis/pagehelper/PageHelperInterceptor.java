package com.xstudio.spring.mybatis.pagehelper;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;

import java.util.Properties;

/**
 * @author xiaobiao
 * @version 2019/9/26
 */
public class PageHelperInterceptor {
    public static Interceptor getInterceptor() {
        // 分页 插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties props = new Properties();
        props.setProperty("dialectClass", "com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect");
        pageInterceptor.setProperties(props);
        return pageInterceptor;
    }
}
