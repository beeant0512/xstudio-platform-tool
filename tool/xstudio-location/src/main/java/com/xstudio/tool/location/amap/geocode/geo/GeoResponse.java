package com.xstudio.tool.location.amap.geocode.geo;

import com.xstudio.tool.location.amap.AmapResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 地理编码接口返回值
 */
@Data
@EqualsAndHashCode(callSuper=false)
@SuppressWarnings("unused")
public class GeoResponse extends AmapResponse {
    /**
     * 返回结果数目
     */
    private String count;
    /**
     * 地理编码信息列表
     */
    private List<Geocode> geocodes;
}
