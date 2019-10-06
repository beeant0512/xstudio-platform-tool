package com.github.miemiedev.mybatis.paginator.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author xiaobiao
 * @date 2018/11/16
 */
public class PageList<E> extends ArrayList<E> {
    private static final long serialVersionUID = 1412759446332294208L;

    private Paginator paginator;

    public PageList() {
    }

    public PageList(Collection<? extends E> c) {
        super(c);
    }


    public PageList(Collection<? extends E> c, Paginator p) {
        super(c);
        this.paginator = p;
    }

    public PageList(Paginator p) {
        this.paginator = p;
    }


    /**
     * 得到分页器，通过Paginator可以得到总页数等值
     *
     * @return
     */
    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(int page, int limit, int totalCount) {
        this.paginator = new Paginator(page, limit, totalCount);
    }
}
