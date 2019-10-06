package com.xstudio.srping.quartz.service;

import com.xstudio.srping.quartz.model.JobAndTrigger;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * @author xiaobiao
 * @version 2017/11/22
 */
public interface IJobAndTriggerService {
    PageList<JobAndTrigger> getJobAndTriggerDetails(JobAndTrigger jobAndTrigger, PageBounds pageBounds);
}
