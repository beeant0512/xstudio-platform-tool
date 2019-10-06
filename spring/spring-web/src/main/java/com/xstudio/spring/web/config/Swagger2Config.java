package com.xstudio.spring.web.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2018/3/1
 */
@ConditionalOnProperty(prefix = "swagger", name = "enable")
@Configuration
class Swagger2Config {
    private static Logger logger = LogManager.getLogger(Swagger2Config.class);
    /**
     * 是否启用swagger
     */
    @Value("${swagger.enable}")
    private Boolean enable;

    /**
     * swagger 文档 title
     */
    @Value("${app.title}")
    private String title;

    /**
     * swagger 文档 描述
     */
    @Value("${app.description}")
    private String description;

    /**
     * swagger 文档 版本
     */
    @Value("${app.version}")
    private String version;

    /**
     * swagger 文档接口扫描的包路径
     */
    @Value("${swagger.package}")
    private String pakage;

    /**
     * Docket: Springfox’s, primary api configuration mechanism is initialized for swagger specification 2.0
     * <p>
     * A builder which is intended to be the primary interface into the swagger-springmvc framework.
     * Provides sensible defaults and convenience methods for configuration.
     *
     * @return void
     */
    @Bean
    public Docket docketApi() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(200).message("接口调用正常").responseModel(new ModelRef("Msg")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(401).message("接口未授权").responseModel(new ModelRef("Msg")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").responseModel(new ModelRef("Msg")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").responseModel(new ModelRef("Msg")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(pakage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }
}
