package com.xstudio.tool.location.amap.geocode.regeo;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Regeocode {
    /**
     * 地址元素
     */
    private AddressComponent addressComponent;
    /**
     * aoi信息列表
     * <p>
     * 请求参数 extensions 为 all 时返回
     */
    private List<Aoi> aois;
    /**
     * 结构化地址信息包括：省份＋城市＋区县＋城镇＋乡村＋街道＋门牌号码
     * <p>
     * 如果坐标点处于海域范围内，则结构化地址信息为：省份＋城市＋区县＋海域信息
     */
    private String formattedAddress;
    /**
     * poi信息列表
     * <p>
     * 请求参数 extensions 为 all 时返回
     */
    private List<Poi> pois;
    /**
     * 道路交叉口列表
     * <p>
     * 请求参数 extensions 为 all 时返回
     */
    private List<Roadinter> roadinters;
    /**
     * 道路信息列表
     * <p>
     * 请求参数 extensions 为 all 时返回
     */
    private List<Road> roads;

}
