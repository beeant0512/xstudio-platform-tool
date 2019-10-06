package com.xstudio.tool.location;

/**
 * com.xstudio.tool.location
 *
 * @author xiaobiao
 * @version 2019/8/15
 */
public class Wgs84 extends LngLat {

    /**
     * @param lng 经度
     * @param lat 纬度
     */
    public Wgs84(Double lng, Double lat) {
        super(lng, lat);
    }

    /**
     * 获取GCJ02坐标
     *
     * @return {@link LngLat}
     */
    public Gcj02 toGcj02() {
        LngLat lngLat = CoordinateUtil.wgs84ToGcj02(this.getLng(), this.getLat());
        return new Gcj02(lngLat.getLng(), lngLat.getLat());
    }

    /**
     * 获取BD09坐标
     *
     * @return {@link LngLat}
     */
    public Bd09 toBd09() {
        LngLat lngLat = CoordinateUtil.wgs84ToBd09(this.getLng(), this.getLat());
        return new Bd09(lngLat.getLng(), lngLat.getLat());
    }
}
