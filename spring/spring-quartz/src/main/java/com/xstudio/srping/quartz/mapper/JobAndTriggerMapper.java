package com.xstudio.srping.quartz.mapper;

import com.xstudio.srping.quartz.model.JobAndTrigger;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaobiao
 * @version 2017/11/22
 */
@Mapper
public interface JobAndTriggerMapper {

    PageList<JobAndTrigger> getJobAndTriggerDetails(@Param("jobAndTrigger") JobAndTrigger jobAndTrigger, @Param("pageBounds") PageBounds pageBounds);
}
