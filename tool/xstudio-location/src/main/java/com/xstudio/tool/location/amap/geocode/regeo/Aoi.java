package com.xstudio.tool.location.amap.geocode.regeo;

import lombok.Data;

/**
 * aoi信息
 */
@Data
@SuppressWarnings("unused")
public class Aoi {
    /**
     * 所属 aoi 所在区域编码
     */
    private String adcode;
    /**
     * 所属aoi点面积 单位：平方米
     */
    private String area;
    /**
     * 输入经纬度是否在aoi面之中
     * 0，代表在aoi内
     * <p>
     * 其余整数代表距离AOI的距离
     */
    private String distance;
    /**
     * 所属 aoi的id
     */
    private String id;
    /**
     * 所属 aoi 中心点坐标
     */
    private String location;
    /**
     * 所属 aoi 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;

}
