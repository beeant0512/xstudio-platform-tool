package com.xstudio.tool.location;

/**
 * 提供了百度坐标（BD09）、国测局坐标（火星坐标，GCJ02）、和WGS84坐标系之间的转换
 * 提供了百度经纬度与百度墨卡托互相转换方法
 * <p>
 * WGS84 坐标系：即地球坐标系，国际上通用的坐标系。
 * GCJ02 坐标系：即火星坐标系，WGS84坐标系经加密后的坐标系。腾讯 高德
 * BD09 坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系。百度坐标系
 * <p>
 * 高德MapABC地图API 火星坐标
 * 腾讯搜搜地图API 火星坐标
 * 阿里云地图API 火星坐标
 * 灵图51ditu地图API 火星坐标
 * 百度地图API 百度坐标
 * 搜狐搜狗地图API 搜狗坐标
 * 图吧MapBar地图API 图吧坐标
 *
 * @author xiaobiao
 */
public class CoordinateUtil {
    /**
     * 长半轴,卫星椭球坐标投影到平面地图坐标系的投影因子
     */
    private static final double SEMI_MAJOR = 6378245.0D;
    /**
     * 圆周率π
     */
    private static final double PI = 3.1415926535897932384626D;
    /**
     * 椭球的偏心率 (A^2 - b^2) / A^2
     */
    private static final double FLATTENING = 0.00669342162296594323D;

    /**
     * 火星坐标系与百度坐标系转换的中间量
     */
    private static final double X_PI = PI * 3000.0 / 180.0;

    private CoordinateUtil() {
    }

    /**
     * WGS84 坐标 转 GCJ02
     * <p>
     * 只在中国大陆的范围的坐标有效，以外直接返回世界标准坐标
     *
     * @param lng 经度
     * @param lat 纬度
     * @return {@link LngLat}
     */
    public static LngLat wgs84ToGcj02(double lng, double lat) {
        if (isOutOfChina(lng, lat)) {
            return new LngLat(lng, lat);
        }
        double[] offset = offset(lng, lat);
        return new LngLat(lng + offset[0], lat + offset[1]);
    }


    /**
     * GCJ02 转百度坐标
     * <p>
     * 即谷歌、高德 转 百度
     *
     * @param lng 经度
     * @param lat 纬度
     * @return 百度坐标：[经度，纬度]
     */
    public static LngLat gcj02ToBd09(double lng, double lat) {
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI);
        return new LngLat(z * Math.cos(theta) + 0.0065, z * Math.sin(theta) + 0.006);
    }

    /**
     * 百度坐标（BD09）转 GCJ02
     * <p>
     * 即 百度 转 谷歌、高德
     *
     * @param lng 经度
     * @param lat 纬度
     * @return GCJ02 坐标：[经度，纬度]
     */
    public static LngLat bd09ToGcj02(double lng, double lat) {
        double x = lng - 0.0065;
        double y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        return new LngLat(z * Math.cos(theta), z * Math.sin(theta));
    }

    /**
     * 百度坐标BD09 转 WGS84 此接口有1－2米左右的误差，需要精确定位情景慎用
     *
     * @param lng 经度
     * @param lat 纬度
     * @return WGS84坐标：[经度，纬度]
     */
    public static LngLat bd09ToWgs84(double lng, double lat) {
        // 先由百度转火星
        LngLat lngLat = bd09ToGcj02(lng, lat);
        // 再将火星转百度
        return gcj02ToWgs84(lngLat.getLng(), lngLat.getLat());
    }

    /**
     * WGS84 转 百度坐标BD09
     *
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    public static LngLat wgs84ToBd09(double lng, double lat) {
        // 先由经纬转火星
        LngLat lngLat = wgs84ToGcj02(lng, lat);
        // 再将火星转百度
        return gcj02ToBd09(lngLat.getLng(), lngLat.getLat());
    }

    /**
     * GCJ02 转 WGS84 (粗略) 此接口有1－2米左右的误差，需要精确定位情景慎用
     *
     * @param lng 经度
     * @param lat 纬度
     * @return WGS84坐标：[经度，纬度]
     */
    public static LngLat gcj02ToWgs84(double lng, double lat) {
        if (isOutOfChina(lng, lat)) {
            return new LngLat(lng, lat);
        }
        double[] offset = offset(lng, lat);
        return new LngLat(lng - offset[0], lat - offset[1]);
    }


    /**
     * GCJ02 转 WGS84 (精确)
     *
     * @param lng 经度
     * @param lat 纬度
     * @return WGS84坐标：[经度，纬度]
     */
    public static LngLat gcj02ToWgs84Exactly(double lng, double lat) {
        double initDelta = 0.01;
        double threshold = 0.000000001;
        double dLat = initDelta;
        double dLng = initDelta;
        double mLat = lat - dLat;
        double mLng = lng - dLng;
        double pLat = lat + dLat;
        double pLng = lng + dLng;
        double wgsLat;
        double wgsLng;
        double i = 0;
        while (true) {
            wgsLat = (mLat + pLat) / 2;
            wgsLng = (mLng + pLng) / 2;
            LngLat lngLat = wgs84ToGcj02(wgsLat, wgsLng);
            dLat = lngLat.getLat() - lat;
            dLng = lngLat.getLng() - lng;
            boolean breakLoop = (Math.abs(dLat) < threshold) && (Math.abs(dLng) < threshold) || ++i > 10000;
            if (breakLoop) {
                break;
            }

            if (dLat > 0) {
                pLat = wgsLat;
            } else {
                mLat = wgsLat;
            }
            if (dLng > 0) {
                pLng = wgsLng;
            } else {
                mLng = wgsLng;
            }
        }

        return new LngLat(wgsLng, wgsLat);
    }

    /**
     * 是否超出中国范围
     *
     * @param lnglat {@link LngLat}
     * @return boolean
     */
    public static boolean isOutOfChina(LngLat lnglat) {
        return isOutOfChina(lnglat.getLng(), lnglat.getLat());
    }

    /**
     * 判断是否在国内，不在国内则不做偏移
     *
     * @param lng GPS lng
     * @param lat GPS lat
     * @return 是否超出
     */
    public static boolean isOutOfChina(double lng, double lat) {
        return (lng < 72.004 || lng > 137.8347) || (lat < 0.8293 || lat > 55.8271);
    }

    /**
     * 保留小数点后6位
     * 高德反解析只支持到小数点后6位
     *
     * @param location 经纬度 逗号拼接
     * @return 处理后，小数点后6的经度或纬度
     */
    public static String format(String location) {
        String[] split = location.split(",");
        if (split.length == 1) {
            return formatLngOrLat(split[0]);
        }
        return formatLngOrLat(split[0]) + "," + formatLngOrLat(split[1]);
    }

    /**
     * 保留小数点后6位
     * 高德反解析只支持到小数点后6位
     *
     * @param lngOrLat 经度或纬度
     * @return 处理后，小数点后6的经度或纬度
     */
    private static String formatLngOrLat(String lngOrLat) {
        int dotPosition = lngOrLat.indexOf('.');
        if (dotPosition + 7 > lngOrLat.length()) {
            return lngOrLat;
        }

        return lngOrLat.substring(0, dotPosition + 7);
    }


    /**
     * 纬度偏移量
     *
     * @param lng 纬度
     * @param lat 经度
     * @return 转换结果
     */
    private static double transformLat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 经度偏移量
     *
     * @param lng 纬度
     * @param lat 经度
     * @return 转换结果
     */
    private static double transformLng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 偏移量计算
     *
     * @param lng 经度
     * @param lat 纬度
     * @return [经度，纬度]
     */
    private static double[] offset(double lng, double lat) {
        double[] lngLat = new double[2];
        double dlng = transformLng(lng - 105.0, lat - 35.0);
        double dlat = transformLat(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - FLATTENING * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlng = (dlng * 180.0) / (SEMI_MAJOR / sqrtmagic * Math.cos(radlat) * PI);
        dlat = (dlat * 180.0) / ((SEMI_MAJOR * (1 - FLATTENING)) / (magic * sqrtmagic) * PI);
        lngLat[0] = dlng;
        lngLat[1] = dlat;
        return lngLat;
    }
}

