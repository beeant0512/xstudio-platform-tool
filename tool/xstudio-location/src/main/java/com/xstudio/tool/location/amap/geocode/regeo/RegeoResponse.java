package com.xstudio.tool.location.amap.geocode.regeo;

import com.xstudio.tool.location.amap.AmapResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@SuppressWarnings("unused")
public class RegeoResponse extends AmapResponse {
    /**
     * 逆地理编码列表
     */
    private Regeocode regeocode;

}
