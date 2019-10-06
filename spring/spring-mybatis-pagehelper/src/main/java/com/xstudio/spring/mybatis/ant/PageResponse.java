package com.xstudio.spring.mybatis.ant;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.tool.utils.Msg;
import com.xstudio.tool.web.Response;

import java.io.Serializable;

/**
 * ant.design 表格组件返回值对象
 *
 * @author xiaobiao
 * @version 1 2017/11/4
 * @see <a href="https://ant.design/components/table-cn/">Table</a>
 */
public class PageResponse<T> extends Response<T> implements Serializable {

    private static final long serialVersionUID = 6323923218194260827L;

    public PageResponse() {
    }

    public PageResponse(Msg<PageList<T>> msg) {
        setList(msg.getData());
    }

    /**
     * Setter for property 'list'.
     *
     * @param list Value to set for property 'list'.
     */
    public void setList(PageList<T> list) {
        super.setList(list);
        if (null != list.getPaginator()) {
            getPagination().setTotal(list.getPaginator().getTotalCount());
            getPagination().setCurrent(list.getPaginator().getPage());
        }
    }
}
