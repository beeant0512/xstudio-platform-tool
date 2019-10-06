package com.xstudio.srping.quartz.rest;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 2017/11/21
 */
public class ScheduleVo implements Serializable {
    private static final long serialVersionUID = 312870785492107684L;
    private String schedule;

    private String startTime;

    private String endTime;

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
