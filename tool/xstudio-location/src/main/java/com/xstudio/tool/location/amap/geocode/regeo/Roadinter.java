
package com.xstudio.tool.location.amap.geocode.regeo;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Roadinter {
    /**
     * 方位 输入点相对路口的方位
     */
    private String direction;
    /**
     * 交叉路口到请求坐标的距离 单位：米
     */
    private String distance;
    /**
     * 第一条道路id
     */
    private String firstId;
    /**
     * 第一条道路名称
     */
    private String firstName;
    /**
     * 路口经纬度
     */
    private String location;
    /**
     * 第二条道路id
     */
    private String secondId;
    /**
     * 第二条道路名称
     */
    private String secondName;
}
