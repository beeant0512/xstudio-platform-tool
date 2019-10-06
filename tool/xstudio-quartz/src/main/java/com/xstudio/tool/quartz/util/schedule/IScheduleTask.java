package com.xstudio.tool.quartz.util.schedule;

import org.quartz.Job;

/**
 *
 * @author xiaobiao
 * @version 1
 * @date 2017/6/23
 * @deprecated 请使用spring-quartz依赖
 */
public interface IScheduleTask extends Job {
    /**
     * 执行某个时间的任务
     * @param date 时间字符串
     */
    void execute(String date);
}
