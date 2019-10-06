package com.xstudio.tool.location.amap.geocode.regeo;

import com.xstudio.tool.location.amap.geocode.Building;
import com.xstudio.tool.location.amap.geocode.Neighborhood;
import lombok.Data;

import java.util.List;

/**
 * 结构化地址信息
 * 结构化地址信息包括：省份＋城市＋区县＋城镇＋乡村＋街道＋门牌号码
 * <p>
 * 如果坐标点处于海域范围内，则结构化地址信息为：省份＋城市＋区县＋海域信息
 */
@Data
@SuppressWarnings("unused")
public class AddressComponent {

    private String adcode;
    private Building building;
    private List<BusinessArea> businessAreas;
    private List<Object> city;
    private String citycode;
    private String country;
    private String district;
    private Neighborhood neighborhood;
    private String province;
    private StreetNumber streetNumber;
    private String towncode;
    private String township;

}
