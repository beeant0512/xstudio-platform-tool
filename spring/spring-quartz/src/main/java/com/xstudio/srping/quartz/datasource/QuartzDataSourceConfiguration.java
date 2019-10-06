package com.xstudio.srping.quartz.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.xstudio.spring.mybatis.SqlSessionFactoryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * carbond 数据库配置
 * <p>
 *
 * @author xiaobiao on 2017/4/7.
 */

@Configuration
@MapperScan(annotationClass = Mapper.class, basePackages = {"com.xstudio.srping.quartz.mapper"}, sqlSessionFactoryRef = "quartzSqlSessionFactory")
public class QuartzDataSourceConfiguration {
    ;

    /**
     * 将MybatisConfig类中初始化的对象注入进来
     */
    @Autowired
    private ConfigurationCustomizer customizer;

    @Bean(name = "quartzDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.quartz")
    public DruidDataSource quartzDataSource() {
        return new DruidDataSource();
    }

    @Bean("quartzSqlSessionFactory")
    public SqlSessionFactory quartzSqlSessionFactory(@Qualifier("quartzDataSource") DataSource dataSource) throws Exception {
        return SqlSessionFactoryUtil.getSqlSessionFactory(dataSource
                , "classpath:/mybatis/mysql/quartz/**.xml"
                , "com.xstudio.srping.quartz.model"
                , customizer);
    }

    @Bean(name = "quartzTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("quartzDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "quartzSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("quartzSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}