package com.xstudio.spring.mybatis;

import com.xstudio.spring.mybatis.paginator.PaginatorInterceptor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @author xiaobiao
 * @version 2019/5/21
 */
@org.springframework.context.annotation.Configuration
public class MybatisConfiguration {
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(Configuration configuration) {
                // customize
                configuration.addInterceptor(PaginatorInterceptor.getInterceptor());
            }
        };
    }
}
