package com.xstudio.tool.location.amap.geocode.regeo;

import lombok.Data;

/**
 * 商圈信息
 */
@Data
@SuppressWarnings("unused")
public class BusinessArea {
    /**
     * 商圈所在区域的adcode
     * 例如：朝阳区/海淀区
     */
    private String id;
    /**
     * 商圈中心点经纬度
     */
    private String location;
    /**
     * 商圈名称
     *  例如：颐和园
     */
    private String name;

}
