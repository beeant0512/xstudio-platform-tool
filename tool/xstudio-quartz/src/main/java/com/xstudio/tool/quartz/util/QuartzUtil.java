package com.xstudio.tool.quartz.util;

import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.utils.Msg;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.calendar.ZoneInfo;

import java.util.TimeZone;

/**
 * @author xiaobiao
 * @version 2019/7/18
 */
public class QuartzUtil {
    private static Scheduler scheduler;
    private static Logger logger = LoggerFactory.getLogger(QuartzUtil.class);

    public static void initScheduler(Scheduler sche) {
        if (null == scheduler) {
            QuartzUtil.scheduler = sche;
        }
    }


    private static Object getClass(String classname) throws Exception {
        Class<?> clasz = Class.forName(classname);
        return clasz.newInstance();
    }

    public static Msg<String> add(JobAndTriggerProperty jobAndTrigger) {
        Msg<String> msg = new Msg<>();
        try {
            // 通过SchedulerFactory获取一个调度器实例
            scheduler.start();
            String jobName = getJobName(jobAndTrigger);
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) getClass(jobAndTrigger.getJobClassName()).getClass())
                    .withIdentity(jobName, jobAndTrigger.getJobGroup())
                    .withDescription(jobAndTrigger.getJobDescription())
                    .build();

            TimeZone timeZone = TimeZone.getTimeZone(jobAndTrigger.getTimeZoneId());
            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobAndTrigger.getCronExpression()).inTimeZone(timeZone);

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getJobName(jobAndTrigger), jobAndTrigger.getTriggerGroup())
                    .withDescription(jobAndTrigger.getJobDescription())
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            logger.error("创建定时任务失败", e);
            msg.setResult(EnError.SERVICE_INVALID);
            msg.setMsg("创建定时任务失败" + e.getMessage());
            logger.error("创建定时任务失败", e);
            return msg;

        }

        return msg;
    }

    public static String getJobName(JobAndTriggerProperty jobAndTrigger) {
        String jobName = jobAndTrigger.getJobClassName();
        if (!StringUtils.isEmpty(jobAndTrigger.getJobName())) {
            jobName = jobAndTrigger.getJobName();
        }
        return jobName;
    }

    public static Msg<String> pause(JobAndTriggerProperty jobAndTrigger) throws Exception {
        // 通过SchedulerFactory获取一个调度器实例
        scheduler.pauseJob(JobKey.jobKey(getJobName(jobAndTrigger), jobAndTrigger.getJobGroup()));
        return new Msg<>();
    }

    public static Msg<String> resume(JobAndTriggerProperty jobAndTrigger) throws Exception {
        // 通过SchedulerFactory获取一个调度器实例
        scheduler.resumeJob(JobKey.jobKey(getJobName(jobAndTrigger), jobAndTrigger.getJobGroup()));
        return new Msg<>();
    }

    public static Msg<String> update(JobAndTriggerProperty jobAndTrigger) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(getJobName(jobAndTrigger), jobAndTrigger.getTriggerGroup());
            TimeZone timeZone = ZoneInfo.getTimeZone("Asia/Shanghai");
            if (jobAndTrigger.getTimeZoneId() != null) {
                timeZone = TimeZone.getTimeZone(jobAndTrigger.getTimeZoneId());
            }

            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobAndTrigger.getCronExpression()).inTimeZone(timeZone);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .withDescription(jobAndTrigger.getJobDescription())
                    .build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            logger.error("更新定时任务失败", e);
            throw new Exception("更新定时任务失败");
        }
        return new Msg<>();
    }

    public static Msg<String> delete(JobAndTriggerProperty jobAndTrigger) throws Exception {
        Msg<String> msg = new Msg<>();
        // 通过SchedulerFactory获取一个调度器实例
        scheduler.pauseTrigger(TriggerKey.triggerKey(getJobName(jobAndTrigger), jobAndTrigger.getTriggerGroup()));
        scheduler.unscheduleJob(TriggerKey.triggerKey(getJobName(jobAndTrigger), jobAndTrigger.getJobGroup()));
        scheduler.deleteJob(JobKey.jobKey(getJobName(jobAndTrigger), jobAndTrigger.getJobGroup()));

        return msg;
    }
}
