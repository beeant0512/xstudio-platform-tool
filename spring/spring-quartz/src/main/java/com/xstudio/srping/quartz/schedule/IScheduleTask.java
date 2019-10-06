package com.xstudio.srping.quartz.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * com.xstudio.schedule
 *
 * @author xiaobiao
 * @version 1
 * @date 2017/6/23
 */
public interface IScheduleTask extends Job {
    /**
     * 执行某个时间的任务
     * @param date 时间字符串
     */
    void execute(String date);
}
