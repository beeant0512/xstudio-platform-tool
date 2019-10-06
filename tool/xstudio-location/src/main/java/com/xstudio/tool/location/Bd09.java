package com.xstudio.tool.location;

/**
 * com.xstudio.tool.location
 *
 * @author xiaobiao
 * @version 2019/8/15
 */
public class Bd09 extends LngLat {
    /**
     * @param lng 经度
     * @param lat 纬度
     */
    public Bd09(Double lng, Double lat) {
        super(lng, lat);
    }

    /**
     * 获取GCJ02坐标
     *
     * @return {@link LngLat}
     */
    public Gcj02 toGcj02() {
        LngLat lngLat = CoordinateUtil.bd09ToGcj02(this.getLng(), this.getLat());
        return new Gcj02(lngLat.getLng(), lngLat.getLat());
    }

    /**
     * 获取WGS84坐标
     *
     * @return {@link LngLat}
     */
    public Wgs84 toWgs84() {
        LngLat lngLat = CoordinateUtil.bd09ToWgs84(this.getLng(), this.getLat());
        return new Wgs84(lngLat.getLng(), lngLat.getLat());
    }
}
