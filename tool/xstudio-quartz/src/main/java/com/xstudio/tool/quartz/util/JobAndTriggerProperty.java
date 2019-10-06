package com.xstudio.tool.quartz.util;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author xiaobiao
 * @version 2017/11/22
 */
@Data
public class JobAndTriggerProperty implements Serializable {

    private static final long serialVersionUID = -1497939274203659748L;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * job执行的class
     */
    private String jobClassName;
    /**
     * 触发器名字
     */
    private String triggerName;
    /**
     * 触发器分组
     */
    private String triggerGroup;
    /**
     * 定时任务状态
     */
    private String triggerState;
    /**
     * 任务描述
     */
    private String jobDescription;
    /**
     * 重复周期
     */
    private BigInteger repeatInterval;
    /**
     *
     */
    private BigInteger timesTriggered;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 时区
     */
    private String timeZoneId = "GMT+08:00";
}
