package com.xstudio.tool.location.amap;

import com.alibaba.fastjson.JSON;
import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.location.amap.convert.ConvertResponse;
import com.xstudio.tool.location.amap.convert.Coordsys;
import com.xstudio.tool.location.amap.geocode.geo.GeoResponse;
import com.xstudio.tool.location.amap.weather.WeatherResponse;
import com.xstudio.tool.request.ClientResponse;
import com.xstudio.tool.request.RequestUtil;
import com.xstudio.tool.utils.Msg;
import org.springframework.http.HttpStatus;

/**
 * 高德WEB API
 * com.xstudio.tool.location.amap
 *
 * @author xiaobiao
 * @version 2019/8/28
 */
public class AmapWebApi {
    /**
     * 高德的Key
     */
    private static String key;

    /**
     * 高德的域名（可以是反代地址）
     * 默认地址： https://restapi.amap.com/v3/
     */
    private static String domain = "https://restapi.amap.com/";

    /**
     * @param address 结构化地址信息
     * @param city    指定查询的城市
     * @param batch   批量查询控制
     * @param sig     数字签名
     * @param output  返回数据格式类型
     * @return Msg<GeoResponse>
     */
    public static Msg<GeoResponse> geo(String address, String city, Boolean batch, String sig, OutputType output) {
        Msg<GeoResponse> msg = new Msg<>();
        StringBuilder sb = new StringBuilder(domain);
        sb.append("v3/geocode/geo?key=");
        sb.append(key);
        if (null != address) {
            sb.append("&address=");
            sb.append(address);
        }
        if (null != city) {
            sb.append("&city=");
            sb.append(city);
        }
        if (null != batch) {
            sb.append("&batch=");
            sb.append(batch);
        }
        if (null != sig) {
            sb.append("&sig=");
            sb.append(sig);
        }
        if (null != output) {
            sb.append("&output=");
            sb.append(output);
        }
        ClientResponse clientResponse = RequestUtil.get(sb.toString());
        if (HttpStatus.OK.value() != clientResponse.getOrigin().getStatusLine().getStatusCode()) {
            msg.setResult(EnError.SERVICE_INVALID);
            msg.setMsg(String.valueOf(clientResponse.getOrigin().getStatusLine().getStatusCode()));
            return msg;
        }
        GeoResponse geoResponse = JSON.parseObject(clientResponse.getContent(), GeoResponse.class);
        if ("0".equals(geoResponse.getStatus())) {
            if (!Infocode.C10000.getCode().equalsIgnoreCase(geoResponse.getInfocode())) {
                msg.setResult(EnError.SERVICE_INVALID);
                msg.setMsg(msg.getMsg() + "," + Infocode.fromCode(geoResponse.getInfocode()).getDescription());
            }
            return msg;
        }
        msg.setData(geoResponse);
        return msg;
    }

    /**
     * @param city       城市编码
     * @param extensions 气象类型 可选值：base/all
     *                   <p>
     *                   base:返回实况天气
     *                   <p>
     *                   all:返回预报天气
     * @param output     返回格式 可选值：JSON,XML
     * @return Msg<WeatherResponse>
     */
    public static Msg<WeatherResponse> weather(String city, String extensions, OutputType output) {
        Msg<WeatherResponse> msg = new Msg<>();
        StringBuilder sb = new StringBuilder(domain);
        sb.append("v3/weather/weatherInfo?key=");
        sb.append(key);
        if (null != city) {
            sb.append("&city=");
            sb.append(city);
        }
        if (null != extensions) {
            sb.append("&extensions=");
            sb.append(extensions);
        }
        if (null != output) {
            sb.append("&output=");
            sb.append(output);
        }
        ClientResponse clientResponse = RequestUtil.get(sb.toString());
        if (HttpStatus.OK.value() != clientResponse.getOrigin().getStatusLine().getStatusCode()) {
            msg.setResult(EnError.SERVICE_INVALID);
            msg.setMsg(String.valueOf(clientResponse.getOrigin().getStatusLine().getStatusCode()));
            return msg;
        }
        WeatherResponse geoResponse = JSON.parseObject(clientResponse.getContent(), WeatherResponse.class);
        if ("0".equals(geoResponse.getStatus())) {
            if (!Infocode.C10000.getCode().equalsIgnoreCase(geoResponse.getInfocode())) {
                msg.setResult(EnError.SERVICE_INVALID);
                msg.setMsg(msg.getMsg() + "," + Infocode.fromCode(geoResponse.getInfocode()).getDescription());
            }
            return msg;
        }
        msg.setData(geoResponse);
        return msg;
    }

    public static Msg<ConvertResponse> convert(String locations, Coordsys coordsys) {
        Msg<ConvertResponse> msg = new Msg<>();
        StringBuilder sb = new StringBuilder(domain);
        sb.append("v3/assistant/coordinate/convert?key=");
        sb.append(key);
        if (null != locations) {
            sb.append("&locations=");
            sb.append(locations);
        }
        if (null != coordsys) {
            sb.append("&coordsys=");
            sb.append(coordsys.name());
        }
        ClientResponse clientResponse = RequestUtil.get(sb.toString());
        if (HttpStatus.OK.value() != clientResponse.getOrigin().getStatusLine().getStatusCode()) {
            msg.setResult(EnError.SERVICE_INVALID);
            msg.setMsg(String.valueOf(clientResponse.getOrigin().getStatusLine().getStatusCode()));
            return msg;
        }

        ConvertResponse geoResponse = JSON.parseObject(clientResponse.getContent(), ConvertResponse.class);
        if ("0".equals(geoResponse.getStatus())) {
            if (!Infocode.C10000.getCode().equalsIgnoreCase(geoResponse.getInfocode())) {
                msg.setResult(EnError.SERVICE_INVALID);
                msg.setMsg(msg.getMsg() + "," + Infocode.fromCode(geoResponse.getInfocode()).getDescription());
            }
            return msg;
        }
        msg.setData(geoResponse);
        return msg;
    }
    /**
     * 设置AMap的Key
     *
     * @param amapKey 高德Key
     */
    public static void setKey(String amapKey) {
        AmapWebApi.key = amapKey;
    }
}
