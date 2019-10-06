package com.xstudio.spring.web.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xstudio.spring.web.converter.DateConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/5/17
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private static final String STATIC_RESOURCE_META_FOLDER = "classpath:/META-INF/templates/";
    private static final String STATIC_RESOURCE_FOLDER = "classpath:/templates/";

    @Value("${spring.resources.cache.period:3600}")
    private Integer cachePeriod;

    /**
     * 添加转换器
     *
     * @param converters 转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 移除jackson2的转换器，解决springboot2中fastjson配置失效问题
        converters.removeIf(next -> next instanceof MappingJackson2HttpMessageConverter);
        // 先定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        // 添加fastjson的配置信息，比如：是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        // 默认所有的Long型都使用 ToStringSerializer
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);
        fastJsonConfig.isWriteContentLength();

        fastJsonConfig.setSerializerFeatures(
                //    输出key是包含双引号
//                SerializerFeature.QuoteFieldNames
                //    是否输出为null的字段,若为null 则显示该字段
//                , SerializerFeature.WriteMapNullValue
                //    数值字段如果为null，则输出为0
//                , SerializerFeature.WriteNullNumberAsZero
                // 空集合返回[],不返回null
//                , SerializerFeature.WriteNullListAsEmpty
                //    字符类型字段如果为null,输出为"",而非null
//                , SerializerFeature.WriteNullStringAsEmpty
                //    Boolean字段如果为null,输出为false,而非null
//                ,SerializerFeature.WriteNullBooleanAsFalse
                // Date的日期转换器
                SerializerFeature.WriteDateUseDateFormat
                // 防止循环引用
//                , SerializerFeature.DisableCircularReferenceDetect
        );

        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

        converters.add(fastJsonHttpMessageConverter);
    }

    /**
     * 静态资源配置
     *
     * @param registry 静态资源注册
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/**.html").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.swf").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.woff").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.ttf").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.svg").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.eot").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.gif").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.jpg").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.js").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.css").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.png").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/**/**.json").addResourceLocations(STATIC_RESOURCE_FOLDER, STATIC_RESOURCE_META_FOLDER).setCachePeriod(cachePeriod);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/templates/static/", "classpath:/META-INF/templates/static/");

        registry.addResourceHandler("/index.html").setCachePeriod(0);

        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 添加日期转换器 保证前端传输的日期格式能够正常的转成日期对象
     * <p>
     * 解决： Failed to convert value of type [java.lang.String] to required type [java.util.Date];
     *
     * @param registry {@link FormatterRegistry}
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }
}
