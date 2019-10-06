
package com.xstudio.tool.location.amap.weather;

import com.xstudio.tool.location.amap.AmapResponse;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class WeatherResponse extends AmapResponse {
    /**
     * 返回结果总数目
     */
    private String count;
    /**
     * 实况天气数据信息
     */
    private List<WeatherLive> lives;

    /**
     * 预报天气信息数据
     */
    private List<WeatherForecast> forecasts;

}
