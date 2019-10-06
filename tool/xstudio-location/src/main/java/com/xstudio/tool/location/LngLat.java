package com.xstudio.tool.location;

import lombok.Data;

import java.io.Serializable;

/**
 * 坐标
 *
 * @author Beeant
 * @version 2019-08-15
 */
@Data
public class LngLat implements Serializable {
    private static final long serialVersionUID = -863826543934317857L;
    /**
     * 经度
     */
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * @param lng 经度
     * @param lat 纬度
     */
    public LngLat(Double lng, Double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    /**
     * 转成double数组格式
     *
     * @return [经度，纬度]
     */
    public double[] toDouble() {
        return new double[]{this.getLng(), this.getLat()};
    }
}
