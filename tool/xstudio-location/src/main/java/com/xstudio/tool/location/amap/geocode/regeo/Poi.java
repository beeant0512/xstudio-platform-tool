package com.xstudio.tool.location.amap.geocode.regeo;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Poi {
    /**
     * poi地址信息
     */
    private String address;
    /**
     * poi所在商圈名称
     */
    private String businessarea;
    /**
     * 方向 为输入点相对建筑物的方位
     */
    private String direction;
    /**
     * 该POI的中心点到请求坐标的距离 单位：米
     */
    private String distance;
    /**
     * poi的id
     */
    private String id;
    /**
     * 坐标点
     */
    private String location;
    /**
     * poi点名称
     */
    private String name;
    private String poiweight;
    /**
     * 电话
     */
    private String tel;
    /**
     * poi类型
     */
    private String type;

}
