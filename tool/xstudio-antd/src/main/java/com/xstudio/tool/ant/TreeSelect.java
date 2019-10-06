package com.xstudio.tool.ant;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2017/12/25
 */
@Data
public class TreeSelect implements Serializable {
    private static final long serialVersionUID = 5699547967271322413L;
    /**
     * 节点显示文字
     */
    private String label;

    /**
     * 节点值
     */
    private String value;

    /**
     * 节点id
     */
    private String key;

    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 子节点列表
     */
    private List<TreeSelect> children;
}
