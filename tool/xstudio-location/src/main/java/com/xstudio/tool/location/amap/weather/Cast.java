package com.xstudio.tool.location.amap.weather;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Cast {
    /**
     * 日期
     */
    private String date;
    /**
     * 白天风力
     */
    @JSONField(name = "daypower")
    private String dayPower;
    /**
     * 白天温度
     */
    @JSONField(name = "daytemp")
    private String dayTemp;
    /**
     * 白天天气现象
     */
    @JSONField(name = "dayweather")
    private String dayWeather;
    /**
     * 白天风向
     */
    @JSONField(name = "daywind")
    private String dayWind;
    /**
     * 晚上风力
     */
    @JSONField(name = "nightpower")
    private String nightPower;
    /**
     * 晚上温度
     */
    @JSONField(name = "nighttemp")
    private String nightTemp;
    /**
     * 晚上天气现象
     */
    @JSONField(name = "nightweather")
    private String nightWeather;
    /**
     * 晚上风向
     */
    @JSONField(name = "nightwind")
    private String nightWind;
    /**
     * 星期几
     */
    private String week;

}
