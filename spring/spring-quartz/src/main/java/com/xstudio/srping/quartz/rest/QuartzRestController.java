package com.xstudio.srping.quartz.rest;

import com.xstudio.tool.enums.EnError;
import com.xstudio.spring.core.ContextUtil;
import com.xstudio.spring.mybatis.ant.PageRequest;
import com.xstudio.spring.mybatis.ant.PageResponse;
import com.xstudio.srping.quartz.model.JobAndTrigger;
import com.xstudio.srping.quartz.properties.QuartzProperty;
import com.xstudio.srping.quartz.schedule.IScheduleTask;
import com.xstudio.srping.quartz.service.IJobAndTriggerService;
import com.xstudio.tool.quartz.util.JobAndTriggerProperty;
import com.xstudio.tool.quartz.util.QuartzUtil;
import com.xstudio.tool.utils.DateUtil;
import com.xstudio.tool.utils.Msg;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaobioa
 * @version 2018/8/6
 */
@RestController
@RequestMapping("inner_api/quartz")
public class QuartzRestController {

    private static Logger logger = LoggerFactory.getLogger(QuartzRestController.class);
    @Autowired
    private QuartzProperty quartzProperty;

    @Autowired
    private IJobAndTriggerService iJobAndTriggerService;

    private void initScheduler(){
        SchedulerFactoryBean bean = ContextUtil.getBean(SchedulerFactoryBean.class);
        Scheduler sched = bean.getObject();
        QuartzUtil.initScheduler(sched);
    }

    /**
     * 添加一个任务
     *
     * @param jobAndTrigger
     * @throws Exception
     */
    @PostMapping(value = "/add")
    public Msg<String> add(@RequestBody JobAndTriggerProperty jobAndTrigger) throws Exception {
        initScheduler();
        jobAndTrigger.setJobGroup(quartzProperty.getGroupName());
        jobAndTrigger.setTriggerGroup(quartzProperty.getGroupName());
        return QuartzUtil.add(jobAndTrigger);
    }

    /**
     * 暂停一个任务
     *
     * @param jobAndTrigger
     * @throws Exception
     */
    @PostMapping(value = "/pause")
    public Msg<String> pause(@RequestBody JobAndTriggerProperty jobAndTrigger) throws Exception {
        initScheduler();
        jobAndTrigger.setJobGroup(quartzProperty.getGroupName());
        jobAndTrigger.setTriggerGroup(quartzProperty.getGroupName());
        return QuartzUtil.pause(jobAndTrigger);
    }

    /**
     * 恢复一个任务
     *
     * @param jobAndTrigger
     * @throws Exception
     */
    @PostMapping(value = "/resume")
    public Msg<String> resume(@RequestBody JobAndTriggerProperty jobAndTrigger) throws Exception {
        initScheduler();
        jobAndTrigger.setJobGroup(quartzProperty.getGroupName());
        jobAndTrigger.setTriggerGroup(quartzProperty.getGroupName());
        return QuartzUtil.resume(jobAndTrigger);
    }

    /**
     * 更新定时任务
     *
     * @param jobAndTrigger
     * @throws Exception
     */
    @PostMapping(value = "/update")
    public Msg<String> update(@RequestBody JobAndTriggerProperty jobAndTrigger) throws Exception {
        initScheduler();
        jobAndTrigger.setJobGroup(quartzProperty.getGroupName());
        jobAndTrigger.setTriggerGroup(quartzProperty.getGroupName());
        return QuartzUtil.update(jobAndTrigger);
    }

    /**
     * 移除一个任务
     *
     * @param jobAndTrigger
     * @throws Exception
     */
    @PostMapping(value = "/delete")
    public Msg<String> delete(@RequestBody JobAndTriggerProperty jobAndTrigger) throws Exception {
        initScheduler();
        jobAndTrigger.setJobGroup(quartzProperty.getGroupName());
        jobAndTrigger.setTriggerGroup(quartzProperty.getGroupName());
        return QuartzUtil.delete(jobAndTrigger);
    }

    /**
     * 定时任务列表
     *
     * @param pageRequest
     * @return
     */
    @GetMapping(value = "/table")
    public Msg<PageResponse<JobAndTrigger>> table(JobAndTrigger jobAndTrigger, PageRequest pageRequest) {
        jobAndTrigger.setJobGroup(quartzProperty.getGroupName());
        jobAndTrigger.setTriggerGroup(quartzProperty.getGroupName());
        PageList<JobAndTrigger> list = iJobAndTriggerService.getJobAndTriggerDetails(jobAndTrigger, pageRequest.getPageBounds());
        Msg<PageResponse<JobAndTrigger>> msg = new Msg<>();

        if (list.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }
        PageResponse pageResponse = new PageResponse();
        pageResponse.setList(list);
        msg.setData(pageResponse);
        return msg;
    }

    /**
     * 按时间范围手动执行定时任务
     * @param schedule {@link ScheduleVo}
     * @return
     */
    @PostMapping("/trigger")
    public Msg<String> schedule(@RequestBody ScheduleVo schedule) {
        IScheduleTask scheduleTask = (IScheduleTask) ContextUtil.getBean(schedule.getSchedule());
        String date = schedule.getStartTime();
        while (DateUtil.isBefore(date, schedule.getEndTime())) {
            scheduleTask.execute(date);
            date = DateUtil.plusDays(date, 1);
        }
        scheduleTask.execute(schedule.getEndTime());
        return new Msg<>();
    }
}
