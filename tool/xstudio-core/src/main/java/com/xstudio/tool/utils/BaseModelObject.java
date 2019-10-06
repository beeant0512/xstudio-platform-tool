package com.xstudio.tool.utils;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 数据库基础对象
 *
 * @author xiaobiao on 2017/2/24.
 */
@Data
public class BaseModelObject<K> implements Serializable {

    private static final long serialVersionUID = -8596287865537997430L;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    /**
     * 更新人ID
     */
    private String updateBy;

    /**
     * 创建开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createAtBegin;

    /**
     * 创建结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createAtEnd;

    /**
     * 更新开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateAtBegin;

    /**
     * 更新介绍时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateAtEnd;

    /**
     * 获取主键
     *
     * @return K
     */
    public K valueOfKey() {
        return null;
    }

    /**
     * 清空主键
     */
    public void assignKeyValue(K value) {
    }
}
