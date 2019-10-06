package com.xstudio.tool.location.amap;

/**
 * com.xstudio.tool.location.amap
 *
 * @author xiaobiao
 * @version 2019/8/28
 */
public enum Infocode {
    C00001("00001", "编码未定义"),
    C10000("10000", "请求正常"),
    C10001("10001", "key不正确或过期"),
    C10002("10002", "没有权限使用相应的服务或者请求接口的路径拼写错误"),
    C10003("10003", "访问已超出日访问量"),
    C10004("10004", " 单位时间内访问过于频繁"),
    C10005("10005", "IP白名单出错，发送请求的服务器IP不在IP白名单内"),
    C10006("10006", "绑定域名无效"),
    C10007("10007", "数字签名未通过验证"),
    C10008("10008", "MD5安全码未通过验证"),
    C10009("10009", "请求key与绑定平台不符"),
    C10010("10010", "IP访问超限"),
    C10011("10011", "服务不支持https请求"),
    C10012("10012", "权限不足，服务请求被拒绝"),
    C10013("10013", "Key被删除"),
    C10014("10014", "云图服务QPS超限"),
    C10015("10015", "受单机QPS限流限制"),
    C10016("10016", "服务器负载过高"),
    C10017("10017", "所请求的资源不可用"),
    C10019("10019", "使用的某个服务总QPS超限"),
    C10020("10020", "某个Key使用某个服务接口QPS超出限制"),
    C10021("10021", "来自于同一IP的访问，使用某个服务QPS超出限制"),
    C10022("10022", "某个Key，来自于同一IP的访问，使用某个服务QPS超出限制"),
    C10023("10023", "某个KeyQPS超出限制"),
    C20000("20000", "请求参数非法"),
    C20001("20001", "缺少必填参数"),
    C20002("20002", "请求协议非法"),
    C20003("20003", "其他未知错误"),
    C20011("20011", "查询坐标或规划点（包括起点、终点、途经点）在海外，但没有海外地图权限"),
    C20012("20012", "查询信息存在非法内容"),
    C20800("20800", "规划点（包括起点、终点、途经点）不在中国陆地范围内"),
    C20801("20801", "划点（起点、终点、途经点）附近搜不到路"),
    C20802("20802", "路线计算失败，通常是由于道路连通关系导致"),
    C20803("20803", "起点终点距离过长。"),
    C30000("30000", "ENGINE_RESPONSE_DATA_ERROR");

    /**
     * 状态描述
     */
    private String description;

    private String code;

    Infocode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    /**
     * 从编码获取枚举对象
     *
     * @param code 编码
     * @return
     */
    public static Infocode fromCode(String code) {
        String sCode = "C" + code;
        for (Infocode infocode : Infocode.values()) {
            if (infocode.getCode().equals(sCode)) {
                return infocode;
            }
        }
        return Infocode.C00001;
    }
}
