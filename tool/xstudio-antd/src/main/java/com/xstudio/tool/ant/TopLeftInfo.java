package com.xstudio.tool.ant;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/11/4
 */
@Data
public class TopLeftInfo implements Serializable{

    private static final long serialVersionUID = 2800780135625787630L;
    /**
     * 姓名
     */
    private String name;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户ID
     */
    private String userid;

    /**
     * 提醒数量
     */
    private Integer notifyCount;
}
