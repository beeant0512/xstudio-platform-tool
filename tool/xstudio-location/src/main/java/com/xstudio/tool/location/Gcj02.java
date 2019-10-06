package com.xstudio.tool.location;

/**
 * com.xstudio.tool.location
 *
 * @author xiaobiao
 * @version 2019/8/15
 */
public class Gcj02 extends LngLat {
    /**
     * @param lng 经度
     * @param lat 纬度
     */
    public Gcj02(Double lng, Double lat) {
        super(lng, lat);
    }

    /**
     * 获取WGS84坐标
     *
     * @return {@link LngLat}
     */
    public Wgs84 toWgs84() {
        LngLat lngLat = CoordinateUtil.gcj02ToWgs84(this.getLng(), this.getLat());
        return new Wgs84(lngLat.getLng(), lngLat.getLat());
    }

    /**
     * 获取WGS84坐标(精确）
     *
     * @return {@link LngLat}
     */
    public Wgs84 toWgs84Exactly() {
        LngLat lngLat = CoordinateUtil.gcj02ToWgs84Exactly(this.getLng(), this.getLat());
        return new Wgs84(lngLat.getLng(), lngLat.getLat());
    }

    /**
     * 获取BD09坐标
     *
     * @return {@link LngLat}
     */
    public Bd09 toBd09() {
        LngLat lngLat = CoordinateUtil.gcj02ToBd09(this.getLng(), this.getLat());
        return new Bd09(lngLat.getLng(), lngLat.getLat());
    }
}
