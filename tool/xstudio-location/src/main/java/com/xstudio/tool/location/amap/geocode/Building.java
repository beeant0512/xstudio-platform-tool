package com.xstudio.tool.location.amap.geocode;

import lombok.Data;

import java.util.List;

/**
 * 楼信息列表
 */
@Data
@SuppressWarnings("unused")
public class Building {
    /**
     * 建筑名称
     * 例如：万达广场
     */
    private List<String> name;
    /**
     * 类型
     * 例如：科教文化服务;学校;高等院校
     */
    private List<String> type;

}
