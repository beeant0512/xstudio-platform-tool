package com.xstudio.srping.quartz.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaobiao
 * @version 2018/9/17
 */
@Configuration
@Data
public class QuartzProperty {

    @Value("${spring.quartz.group:'default'}")
    private String groupName;

    @Value("${spring.quartz.instanceName:'defaultSchedulerName'}")
    private String instanceName;
}
