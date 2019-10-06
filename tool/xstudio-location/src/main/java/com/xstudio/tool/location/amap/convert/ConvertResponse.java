package com.xstudio.tool.location.amap.convert;

import com.xstudio.tool.location.LngLat;
import com.xstudio.tool.location.amap.AmapResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 坐标转换返回对象
 *
 * @author xiaobiao
 */
@Data
public class ConvertResponse extends AmapResponse {
    /**
     * 转换之后的坐标。若有多个坐标，则用 “;”进行区分和间隔
     */
    private String locations;

    /**
     * 返回坐标列表
     *
     * @return {@link LngLat}
     */
    public List<LngLat> lngLatList() {
        String[] split = locations.split(";");
        List<LngLat> list = new ArrayList<>(split.length);
        String[] splitLocation;
        for (String location : split) {
            splitLocation = location.split(",");
            list.add(new LngLat(Double.valueOf(splitLocation[0]), Double.valueOf(splitLocation[1])));
        }

        return list;
    }
}
