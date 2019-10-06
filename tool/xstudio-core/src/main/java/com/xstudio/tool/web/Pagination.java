package com.xstudio.tool.web;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/11/4
 */
@Data
public class Pagination implements Serializable{

    private static final long serialVersionUID = -9122189141426414394L;
    /**
     * 数据总数
     */
    private Integer total = 0;
    /**
     * 当前页
     */
    private Integer current = 0;

    /**
     * 每页条数
     */
    private Integer pageSize;

    public Pagination() {
    }

    /**
     *
     * @param total 总数
     * @param current 当前页
     */
    public Pagination(Integer total, Integer current) {
        this.total = total;
        this.current = current;
    }

    /**
     * @param total   总数
     * @param current 当前页
     */
    public Pagination(Integer total, Integer current, Integer pageSize) {
        this.total = total;
        this.current = current;
        this.pageSize = pageSize;
    }
}
