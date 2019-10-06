package com.xstudio.srping.aop.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaobiao
 * @date 2018/11/8
 */
@Data
public class Log implements Serializable {
    private static final long serialVersionUID = -8152457962486663628L;
    /**
     * 执行方法的类
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 输入参数
     */
    private Object inputs;

    /**
     * 输出参数
     */
    private Object outputs;

    /**
     * 执行耗时
     */
    private Long executeTime;

    /**
     * 行为人名称
     */
    private String actionName;

    /**
     * 执行时间
     */
    private Date actionTime;

    /**
     * IP
     */
    private String ip;

    /**
     * 客户端信息
     */
    private String agent;
}
