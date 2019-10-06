package com.xstudio.spring.security.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaobiao
 * @version 2017/12/18
 */
@Data
public class Lock implements Serializable {

    private static final long serialVersionUID = -5825415362922315024L;
    private Integer failedTimes = 0;

    private Long expireTime;

    private Date firstTime;

    public Lock(Long expireTime) {
        this.expireTime = expireTime;
        this.firstTime = new Date();
    }
}
