package com.xstudio.tool.location;

/**
 * 坐标系枚举
 * facade中使用 其他地方不使用这个枚举 @by xiaobiao
 *
 * @author Bobi on 2016/2/26.
 */
public enum CoordType {
    /**
     * GPS坐标系
     */
    WGS84(1),
    /**
     * 火星坐标系
     */
    GCJ02(2),
    /**
     * 百度坐标系
     */
    BD09(3);

    private int value;

    CoordType(int value) {
        this.value = value;
    }

    public static CoordType valueOf(int value) {
        switch (value) {
            case 1:
                return WGS84;
            case 2:
                return GCJ02;
            default:
                return BD09;
        }
    }

    /**
     * Getter for property 'value'.
     *
     * @return Value for property 'value'.
     */
    public int getValue() {
        return value;
    }
}
