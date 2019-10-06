package com.xstudio.srping.quartz.service.impl;

import com.xstudio.srping.quartz.mapper.JobAndTriggerMapper;
import com.xstudio.srping.quartz.model.JobAndTrigger;
import com.xstudio.srping.quartz.service.IJobAndTriggerService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaobiao
 * @version 2017/11/22
 */
@Service
public class JobAndTriggerImpl implements IJobAndTriggerService {

    @Autowired
    private JobAndTriggerMapper jobAndTriggerMapper;

    @Override
    public PageList<JobAndTrigger> getJobAndTriggerDetails(JobAndTrigger jobAndTrigger, PageBounds pageBounds) {
        return jobAndTriggerMapper.getJobAndTriggerDetails(jobAndTrigger, pageBounds);
    }
}
