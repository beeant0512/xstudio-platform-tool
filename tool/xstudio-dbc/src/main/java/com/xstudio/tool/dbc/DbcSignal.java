package com.xstudio.tool.dbc;

import java.io.Serializable;
import java.util.regex.Matcher;

/**
 * @author xiaobiao
 * @version 2018/9/11
 */
public class DbcSignal implements Serializable {
    private static final long serialVersionUID = 7596449612918084842L;

    /**
     * 信号名称
     */
    private String signalName;

    private String multiplexerIndicator;

    /**
     * 开始位 unsigned_integer
     */
    private Integer startBit;

    /**
     * 信号长度 unsigned_integer
     */
    private Integer signalSize;

    /**
     * 编码方式  '0' | '1' ; (* 0=little endian, 1=big endian *)
     */
    private Integer byteOrder;

    /**
     * 是否小端
     */
    private Boolean isLittleEndian;

    /**
     * 数据类型  (* +=unsigned, -=signed *)
     */
    private String valueType;

    /**
     * 精度
     */
    private Double factor;

    /**
     * 偏移量
     */
    private Double offset;

    /**
     * 最小值
     */
    private Double minimum;

    /**
     * 最大值
     */
    private Double maximum;

    /**
     * 单位
     */
    private String unit;

    /**
     * 接收节点
     */
    private String receiver;

    public DbcSignal() {
    }

    public DbcSignal(Matcher matcher) {
        this.setSignalName(matcher.group(1));
        this.setStartBit(Integer.valueOf(matcher.group(3)));
        this.setSignalSize(Integer.valueOf(matcher.group(4)));
        this.setByteOrder(Integer.valueOf(matcher.group(5)));
        this.setLittleEndian(this.getByteOrder() == 1);
        this.setValueType(matcher.group(6));
        this.setFactor(Double.valueOf(matcher.group(7)));
        this.setOffset(Double.valueOf(matcher.group(8)));
        this.setMinimum(Double.valueOf(matcher.group(9)));
        this.setMaximum(Double.valueOf(matcher.group(10)));
        this.setUnit(matcher.group(11));
        this.setReceiver(matcher.group(12));
    }

    /**
     * Getter for property 'signalName'.
     *
     * @return Value for property 'signalName'.
     */
    public String getSignalName() {
        return signalName;
    }

    /**
     * Setter for property 'signalName'.
     *
     * @param signalName Value to set for property 'signalName'.
     */
    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }

    /**
     * Getter for property 'multiplexerIndicator'.
     *
     * @return Value for property 'multiplexerIndicator'.
     */
    public String getMultiplexerIndicator() {
        return multiplexerIndicator;
    }

    /**
     * Setter for property 'multiplexerIndicator'.
     *
     * @param multiplexerIndicator Value to set for property 'multiplexerIndicator'.
     */
    public void setMultiplexerIndicator(String multiplexerIndicator) {
        this.multiplexerIndicator = multiplexerIndicator;
    }

    /**
     * Getter for property 'startBit'.
     *
     * @return Value for property 'startBit'.
     */
    public Integer getStartBit() {
        return startBit;
    }

    /**
     * Setter for property 'startBit'.
     *
     * @param startBit Value to set for property 'startBit'.
     */
    public void setStartBit(Integer startBit) {
        this.startBit = startBit;
    }

    /**
     * Getter for property 'signalSize'.
     *
     * @return Value for property 'signalSize'.
     */
    public Integer getSignalSize() {
        return signalSize;
    }

    /**
     * Setter for property 'signalSize'.
     *
     * @param signalSize Value to set for property 'signalSize'.
     */
    public void setSignalSize(Integer signalSize) {
        this.signalSize = signalSize;
    }

    /**
     * Getter for property 'byteOrder'.
     *
     * @return Value for property 'byteOrder'.
     */
    public Integer getByteOrder() {
        return byteOrder;
    }

    /**
     * Setter for property 'byteOrder'.
     *
     * @param byteOrder Value to set for property 'byteOrder'.
     */
    public void setByteOrder(Integer byteOrder) {
        this.byteOrder = byteOrder;
    }

    /**
     * Getter for property 'valueType'.
     *
     * @return Value for property 'valueType'.
     */
    public String getValueType() {
        return valueType;
    }

    /**
     * Setter for property 'valueType'.
     *
     * @param valueType Value to set for property 'valueType'.
     */
    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    /**
     * Getter for property 'factor'.
     *
     * @return Value for property 'factor'.
     */
    public Double getFactor() {
        return factor;
    }

    /**
     * Setter for property 'factor'.
     *
     * @param factor Value to set for property 'factor'.
     */
    public void setFactor(Double factor) {
        this.factor = factor;
    }

    /**
     * Getter for property 'offset'.
     *
     * @return Value for property 'offset'.
     */
    public Double getOffset() {
        return offset;
    }

    /**
     * Setter for property 'offset'.
     *
     * @param offset Value to set for property 'offset'.
     */
    public void setOffset(Double offset) {
        this.offset = offset;
    }

    /**
     * Getter for property 'minimum'.
     *
     * @return Value for property 'minimum'.
     */
    public Double getMinimum() {
        return minimum;
    }

    /**
     * Setter for property 'minimum'.
     *
     * @param minimum Value to set for property 'minimum'.
     */
    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    /**
     * Getter for property 'maximum'.
     *
     * @return Value for property 'maximum'.
     */
    public Double getMaximum() {
        return maximum;
    }

    /**
     * Setter for property 'maximum'.
     *
     * @param maximum Value to set for property 'maximum'.
     */
    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }

    /**
     * Getter for property 'unit'.
     *
     * @return Value for property 'unit'.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Setter for property 'unit'.
     *
     * @param unit Value to set for property 'unit'.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Getter for property 'receiver'.
     *
     * @return Value for property 'receiver'.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Setter for property 'receiver'.
     *
     * @param receiver Value to set for property 'receiver'.
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Getter for property 'littleEndian'.
     *
     * @return Value for property 'littleEndian'.
     */
    public Boolean getLittleEndian() {
        return isLittleEndian;
    }

    /**
     * Setter for property 'littleEndian'.
     *
     * @param littleEndian Value to set for property 'littleEndian'.
     */
    public void setLittleEndian(Boolean littleEndian) {
        isLittleEndian = littleEndian;
    }

    public Object translate(String binaryString) {
        String value;
        if (byteOrder == 1) {
            value = binaryString.substring(startBit, startBit + signalSize);
        } else {
            value = binaryString.substring(startBit - signalSize, startBit);
        }
        Integer rawValue = Integer.valueOf(value, 2);
        if (factor == 1.0) {
            return rawValue + offset.intValue();
        } else {
            Double doubleValue = Double.valueOf(rawValue);
            return doubleValue * factor + offset;
        }
    }
}
