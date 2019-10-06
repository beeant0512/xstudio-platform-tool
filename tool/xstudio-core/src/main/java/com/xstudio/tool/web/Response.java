package com.xstudio.tool.web;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ant.design 表格组件返回值对象
 *
 * @author xiaobiao
 * @version 1
 * @date 2017/11/4
 * @see <a href="https://ant.design/components/table-cn/">Table</a>
 */
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 6323923218194260827L;
    /**
     * 表格渲染值
     */
    private List<T> list;

    /**
     * 分页信息
     */
    private Pagination pagination = new Pagination();


    /**
     * Setter for property 'list'.
     *
     * @param list Value to set for property 'list'.
     */
    public void setList(List<T> list) {
        this.list = list;
        if (null == this.pagination) {
            this.pagination = new Pagination();
            pagination.setTotal(list.size());
            pagination.setCurrent(1);
        }
    }


    /**
     * Setter for property 'pagination'.
     *
     * @param total 总数
     * @param page  当前页
     */
    public void setPagination(Integer total, Integer page) {
        this.pagination.setTotal(total);
        this.pagination.setCurrent(page);
    }
}
