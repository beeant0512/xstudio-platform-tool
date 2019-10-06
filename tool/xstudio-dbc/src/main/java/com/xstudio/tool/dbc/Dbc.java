package com.xstudio.tool.dbc;

import java.util.HashMap;

/**
 * @author xiaobiao
 * @version 2018/9/11
 */
public class Dbc {

    /**
     * 所有message
     */
    private HashMap<Integer, DbcMessage> messages = new HashMap<>();

    /**
     * DBC名字
     */
    private String dbcName;

    /**
     * 车系
     */
    private String seriesCode;

    /**
     * 版本信息
     */
    private String version;


    private Dbc() {
    }

    public Dbc(String name) {
        this.dbcName = name;
    }

    /**
     * Getter for property 'messages'.
     *
     * @return Value for property 'messages'.
     */
    public HashMap<Integer, DbcMessage> getMessages() {
        return messages;
    }

    /**
     * Setter for property 'messages'.
     *
     * @param messages Value to set for property 'messages'.
     */
    public void setMessages(HashMap<Integer, DbcMessage> messages) {
        this.messages = messages;
    }

    /**
     * Getter for property 'dbcName'.
     *
     * @return Value for property 'dbcName'.
     */
    public String getDbcName() {
        return dbcName;
    }

    /**
     * Setter for property 'dbcName'.
     *
     * @param dbcName Value to set for property 'dbcName'.
     */
    public void setDbcName(String dbcName) {
        this.dbcName = dbcName;
    }

    /**
     * Getter for property 'seriesCode'.
     *
     * @return Value for property 'seriesCode'.
     */
    public String getSeriesCode() {
        return seriesCode;
    }

    /**
     * Setter for property 'seriesCode'.
     *
     * @param seriesCode Value to set for property 'seriesCode'.
     */
    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    /**
     * Getter for property 'version'.
     *
     * @return Value for property 'version'.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Setter for property 'version'.
     *
     * @param version Value to set for property 'version'.
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
