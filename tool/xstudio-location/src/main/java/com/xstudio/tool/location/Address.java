package com.xstudio.tool.location;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 地址
 *
 * @author Beeant
 */
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = -4576003631502159233L;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 经度
     */
    private double lng;

    /**
     * 地点名称
     */
    private String name;

    /**
     * 国家
     */
    private String country;

    /**
     * 省市
     */
    private String province;

    /**
     * 城市
     */
    private List<String> city;

    /**
     * 城市编码
     */
    private String citycode;

    /**
     * 乡镇
     */
    private String district;

    /**
     * 区域编码（身份证前6位）
     */
    private String adcode;

    /**
     * 坐标类型，取值可通过EnCoordType.toString
     */
    private String coordType;


    public Address(double lng, double lat) {
        super();
        this.lng = lng;
        this.lat = lat;
    }

    public Address(double lng, double lat, String coordType) {
        super();
        this.lng = lng;
        this.lat = lat;
        this.coordType = coordType;
    }

    public Address() {
    }
}
