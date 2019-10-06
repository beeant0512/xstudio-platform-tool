package com.xstudio.spring.mybatis.paginator;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.service.AbstractServiceImpl;
import com.xstudio.tool.utils.BaseModelObject;
import com.xstudio.tool.utils.Msg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/5/18
 */
public abstract class MybatisPaginatorServiceImpl<T extends BaseModelObject, K> extends AbstractServiceImpl<T, K, PageBounds, PageList<T>, PageList<T>> {
    @Override
    public Msg<PageList<T>> fuzzySearch(T record) {
        PageList<Order> orders = new PageList<>();
        orders.add(new Order("create_at", Order.Direction.DESC, ""));

        Msg<PageList<T>> msg = new Msg<>();
        Msg<PageList<T>> pageListMsg = new Msg<>();
        List<T> list = new ArrayList<>();
        Paginator paginator = new Paginator(1, 1, 1);
        int limit = 5000;
        PageBounds pageBounds = new PageBounds(1, limit);
        pageBounds.setOrders((List<Order>) orders);
        boolean doLoop = true;
        while (Boolean.TRUE.equals(pageListMsg.getSuccess()) && doLoop) {
            pageListMsg = fuzzySearchByPager(record, pageBounds);
            if (Boolean.TRUE.equals(pageListMsg.getSuccess())) {
                doLoop = pageListMsg.getData().size() >= limit;
                list.addAll(pageListMsg.getData());
                paginator = pageListMsg.getData().getPaginator();
            }

            pageBounds.setPage(pageBounds.getPage() + 1);
        }

        if (list.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }
        PageList<T> pageList = new PageList<>(list, paginator);
        msg.setData(pageList);
        return msg;
    }

    @Override
    public Msg<PageList<T>> fuzzySearchByPager(T record, PageBounds pageBounds) {
        Msg<PageList<T>> msg = new Msg<>();
        if (null == pageBounds.getOrders() || pageBounds.getOrders().isEmpty()) {
            List<Order> orders = new ArrayList<>();
            orders.add(new Order("create_at", Order.Direction.DESC, ""));
            pageBounds.setOrders(orders);
        }
        PageList<T> result = (PageList<T>) getRepositoryDao().fuzzySearchByPager(record, pageBounds);
        if (result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<PageList<T>> selectAllByExample(T record) {
        PageList<Order> orders = new PageList<>();
        orders.add(new Order("create_at", Order.Direction.DESC, ""));
        return selectAllByExample(record, orders);
    }


    @Override
    public Msg<PageList<T>> selectAllByExample(T record, List<?> orders) {
        Msg<PageList<T>> msg = new Msg<>();
        Msg<PageList<T>> pageListMsg = new Msg<>();
        List<T> list = new ArrayList<>();
        Paginator paginator = new Paginator(1, 1, 1);
        int limit = 5000;
        PageBounds pageBounds = new PageBounds(1, limit);
        pageBounds.setOrders((List<Order>) orders);
        boolean doLoop = true;
        while (Boolean.TRUE.equals(pageListMsg.getSuccess()) && doLoop) {
            pageListMsg = selectByExampleByPager(record, pageBounds);
            if (Boolean.TRUE.equals(pageListMsg.getSuccess())) {
                doLoop = pageListMsg.getData().size() >= limit;
                list.addAll(pageListMsg.getData());
                paginator = pageListMsg.getData().getPaginator();
            }

            pageBounds.setPage(pageBounds.getPage() + 1);
        }

        if (list.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }
        PageList<T> pageList = new PageList<>(list, paginator);
        msg.setData(pageList);
        return msg;
    }

    @Override
    public Msg<PageList<T>> selectByExampleByPager(T record, PageBounds pageBounds) {
        Msg<PageList<T>> msg = new Msg<>();
        PageList<T> result = (PageList<T>) getRepositoryDao().selectByExampleByPager(record, pageBounds);
        if (result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }
}
