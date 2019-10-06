package com.xstudio.spring.mybatis.paginator;

import com.xstudio.spring.mybatis.MyOffsetLimitInterceptor;
import org.apache.ibatis.plugin.Interceptor;

import java.util.Properties;

/**
 * @author xiaobiao
 * @version 2019/5/18
 */
public class PaginatorInterceptor {
    public static Interceptor getInterceptor() {
        // 分页 插件
        MyOffsetLimitInterceptor offsetLimitInterceptor = new MyOffsetLimitInterceptor();
        Properties props = new Properties();
        props.setProperty("dialectClass", "com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect");
        offsetLimitInterceptor.setProperties(props);
        return offsetLimitInterceptor;
    }
}
