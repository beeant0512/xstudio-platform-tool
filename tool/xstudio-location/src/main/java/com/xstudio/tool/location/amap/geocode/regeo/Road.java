package com.xstudio.tool.location.amap.geocode.regeo;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Road {
    /**
     * 方位 输入点和此路的相对方位
     */
    private String direction;
    /**
     * 道路到请求坐标的距离 单位：米
     */
    private String distance;
    /**
     * 道路id
     */
    private String id;
    /**
     * 坐标点
     */
    private String location;
    /**
     * 道路名称
     */
    private String name;

}
