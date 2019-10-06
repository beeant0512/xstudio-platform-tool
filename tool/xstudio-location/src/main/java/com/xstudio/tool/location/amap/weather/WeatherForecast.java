package com.xstudio.tool.location.amap.weather;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 预报天气信息数据
 *
 * @author xiaobiao
 */
@Data
@SuppressWarnings("unused")
public class WeatherForecast {
    /**
     * 城市编码
     */
    private String adcode;
    /**
     * 预报数据list结构，元素cast,按顺序为当天、第二天、第三天的预报数据
     */
    private List<Cast> casts;
    /**
     * 城市名称
     */
    private String city;
    /**
     * 省份名称
     */
    private String province;
    /**
     * 预报发布时间
     */
    @JSONField(name = "reporttime")
    private String reportTime;

}
