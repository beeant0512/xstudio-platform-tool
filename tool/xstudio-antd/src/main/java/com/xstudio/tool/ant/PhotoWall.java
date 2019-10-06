package com.xstudio.tool.ant;

import lombok.Data;

import java.io.Serializable;

/**
 * ant.design 照片墙返回对象
 *
 * @author xiaobiao
 * @version 2018/1/23
 */
@Data
public class PhotoWall implements Serializable {

    private static final long serialVersionUID = -3107187952595288288L;

    /**
     * 图片ID
     */
    private String uid;

    /**
     * 图片名称
     */
    private String name;

    /**
     * 上传状态
     */
    private String status = "done";

    /**
     * 图片下载地址
     */
    private String url;

    /**
     * 缩略图地址
     */
    private String thumbUrl;

    public PhotoWall() {
    }

    /**
     *
     * @param uid
     * @param name
     * @param url
     */
    public PhotoWall(String uid, String name, String url) {
        this.uid = uid;
        this.name = name;
        this.url = url;
    }

    /**
     *
     * @param uid
     * @param url
     */
    public PhotoWall(String uid, String url) {
        this.uid = uid;
        this.url = url;
    }
}
