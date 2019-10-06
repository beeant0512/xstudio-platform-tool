package com.xstudio.tool.ant;

import lombok.Data;

import java.io.Serializable;

/**
 * ant.design 表格组件请求参数对象
 *
 * @author xiaobiao
 * @version 1
 * @date 2017/11/4
 * @see <a href="https://ant.design/components/table-cn/">Table</a>
 */
@Data
public class Request implements Serializable {
    private static final long serialVersionUID = -3783512820125403682L;
    /**
     * 当前页
     */
    private Integer currentPage = 0;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String sorter = "";

    /**
     * 获取当前页
     *
     * @return
     */
    public Integer getCurrentPage() {
        if (currentPage == null) {
            return 0;
        }
        return currentPage;
    }

    /**
     * 获取每页数量， 默认1000
     *
     * @return
     */
    public Integer getPageSize() {
        if (null == pageSize) {
            return 1000;
        }
        return pageSize;
    }

}
