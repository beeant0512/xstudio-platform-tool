package com.xstudio.tool.location.amap.geocode.regeo;

import lombok.Data;

/**
 * 门牌信息列表
 */
@Data
@SuppressWarnings("unused")
public class StreetNumber {

    /**
     * 方向
     * 坐标点所处街道方位
     */
    private String direction;
    /**
     * 门牌地址到请求坐标的距离
     * 单位：米
     */
    private String distance;
    /**
     * 坐标点
     * 经纬度坐标点：经度，纬度
     */
    private String location;
    /**
     * 门牌号
     * 例如：3号
     */
    private String number;
    /**
     * 街道名称
     * 例如：中关村北二条
     */
    private String street;

}
