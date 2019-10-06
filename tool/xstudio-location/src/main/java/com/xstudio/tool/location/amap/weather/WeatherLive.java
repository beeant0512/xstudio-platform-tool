package com.xstudio.tool.location.amap.weather;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 实况天气数据信息
 *
 * @author xiaobiao
 */
@Data
public class WeatherLive {
    /**
     * 区域编码
     */
    private String adcode;
    /**
     * 城市名
     */
    private String city;
    /**
     * 空气湿度
     */
    private String humidity;
    /**
     * 省份名
     */
    private String province;
    /**
     * 数据发布的时间
     */
    @JSONField(name = "reporttime")
    private String reportTime;
    /**
     * 实时气温，单位：摄氏度
     */
    private String temperature;
    /**
     * 天气现象（汉字描述）
     */
    private String weather;
    /**
     * 风向描述
     */
    @JSONField(name = "winddirection")
    private String windDirection;
    /**
     * 风力级别，单位：级
     */
    @JSONField(name = "windpower")
    private String windPower;
}
