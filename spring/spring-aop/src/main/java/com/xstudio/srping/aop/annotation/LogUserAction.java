package com.xstudio.srping.aop.annotation;

import com.xstudio.tool.service.IAbstractService;
import com.xstudio.srping.aop.log.ILogUserActionService;

import java.lang.annotation.*;

/**
 * 用户操作行为日志
 *
 * @author xiaobiao
 * @version 1.0.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogUserAction {
    /**
     * 日志存储服务类
     *
     * @return
     */
    Class service() default ILogUserActionService.class;

    /**
     * 行为名称
     *
     * @return
     */
    String actionName();

    boolean delete() default false;

    Class selectService() default IAbstractService.class;

    String id() default "";
}