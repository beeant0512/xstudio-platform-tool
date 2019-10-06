package com.xstudio.tool.utils.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/6/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Tree implements Serializable {

    private static final long serialVersionUID = -7703093013250996035L;

    private String id;

    private String pid;

    private Integer grade;

    private Boolean isLeaf = false;

    private List<Tree> children;

    public Tree() {
    }

    public Tree(String id, String pid) {
        this.id = id;
        this.pid = pid;
    }
}
