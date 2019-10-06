package com.xstudio.tool.dbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DBC Message 对象
 *
 * @author xiaobiao
 * @version 2018/9/11
 */
public class DbcMessage implements Serializable {

    private static final long serialVersionUID = 1027726088822752291L;

    /**
     * CAN 报文ID 十进制
     */
    private Integer messageId;

    /**
     * CAN 报文名称
     */
    private String messageName;

    /**
     * CAN 报文长度
     */
    private Integer messageSize;

    /**
     *
     */
    private String transmitter;

    /**
     * 信号集
     */
    private List<DbcSignal> signals = new ArrayList<>();

    /**
     * Getter for property 'messageId'.
     *
     * @return Value for property 'messageId'.
     */
    public Integer getMessageId() {
        return messageId;
    }

    /**
     * Setter for property 'messageId'.
     *
     * @param messageId Value to set for property 'messageId'.
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    /**
     * Getter for property 'messageName'.
     *
     * @return Value for property 'messageName'.
     */
    public String getMessageName() {
        return messageName;
    }

    /**
     * Setter for property 'messageName'.
     *
     * @param messageName Value to set for property 'messageName'.
     */
    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    /**
     * Getter for property 'messageSize'.
     *
     * @return Value for property 'messageSize'.
     */
    public Integer getMessageSize() {
        return messageSize;
    }

    /**
     * Setter for property 'messageSize'.
     *
     * @param messageSize Value to set for property 'messageSize'.
     */
    public void setMessageSize(Integer messageSize) {
        this.messageSize = messageSize;
    }

    /**
     * Getter for property 'transmitter'.
     *
     * @return Value for property 'transmitter'.
     */
    public String getTransmitter() {
        return transmitter;
    }

    /**
     * Setter for property 'transmitter'.
     *
     * @param transmitter Value to set for property 'transmitter'.
     */
    public void setTransmitter(String transmitter) {
        this.transmitter = transmitter;
    }

    /**
     * Getter for property 'signals'.
     *
     * @return Value for property 'signals'.
     */
    public List<DbcSignal> getSignals() {
        return signals;
    }

    /**
     * Setter for property 'signals'.
     *
     * @param signals Value to set for property 'signals'.
     */
    public void setSignals(List<DbcSignal> signals) {
        this.signals = signals;
    }

    public void addSignal(DbcSignal signal) {
        this.signals.add(signal);
    }

    /**
     * @param hexString 16进制数据字符串
     * @return
     */
    public HashMap<String, Object> translate(String hexString) {
        // 16进制字符串转成二进制字符串
        String binaryString = hex2binary(hexString);

        HashMap<String, Object> values = new HashMap<>(getSignals().size());
        for (DbcSignal signal : this.getSignals()) {
            values.put(signal.getSignalName(), signal.translate(binaryString));
        }
        return values;
    }

    public static String hex2binary(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }
}
