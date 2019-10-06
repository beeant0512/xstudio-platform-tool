package com.xstudio.tool.location.amap;

import lombok.Data;

/**
 * 高德地图通用返回值
 * com.xstudio.tool.location.amap
 *
 * @author xiaobiao
 * @version 2019/8/28
 */
@Data
@SuppressWarnings("unused")
public class AmapResponse {
    /**
     * 返回状态说明
     * 当 status 为 0 时，info 会返回具体错误原因，否则返回“OK”。详情可以参阅info状态表
     */
    private String info;
    /**
     * 错误码
     */
    private String infocode;
    /**
     * 返回结果状态值
     * 返回值为 0 或 1，0 表示请求失败；1 表示请求成功。
     */
    private String status;
}
