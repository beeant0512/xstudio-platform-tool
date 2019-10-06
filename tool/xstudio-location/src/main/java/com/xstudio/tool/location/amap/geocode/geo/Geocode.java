package com.xstudio.tool.location.amap.geocode.geo;

import com.xstudio.tool.location.amap.geocode.Building;
import com.xstudio.tool.location.amap.geocode.Neighborhood;
import lombok.Data;

import java.util.List;

/**
 * 地理编码信息
 */
@Data
@SuppressWarnings("unused")
public class Geocode {
    /**
     * 区域编码
     * 例如：110101
     */
    private String adcode;
    /**
     * 楼信息列表
     */
    private Building building;
    /**
     * 地址所在的城市名
     * 例如：北京市
     */
    private String city;
    /**
     * 城市编码
     * 例如：010
     */
    private String citycode;
    /**
     * 国家
     * 国内地址默认返回中国
     */
    private String country;
    /**
     * 地址所在的区
     * 例如：朝阳区
     */
    private String district;
    /**
     * 结构化地址信息
     */
    private String formattedAddress;
    /**
     * 匹配级别
     */
    private String level;
    /**
     * 坐标点
     * 经度，纬度
     */
    private String location;
    /**
     * 社区信息列表
     */
    private Neighborhood neighborhood;
    /**
     * 门牌
     * 例如：6号
     */
    private String number;
    /**
     * 地址所在的省份名
     * 例如：北京市。此处需要注意的是，中国的四大直辖市也算作省级单位。
     */
    private String province;
    /**
     * 街道
     * 例如：阜通东大街
     */
    private String street;
    /**
     * 坐标点所在乡镇/街道（此街道为社区街道，不是道路信息）
     * 例如：燕园街道
     */
    private List<Object> township;

}
