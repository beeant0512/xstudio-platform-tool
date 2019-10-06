package com.xstudio.spring.mybatis.pagehelper;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
public abstract class AbstractMybatisPageHelperServiceImpl<T extends BaseModelObject, K> extends AbstractServiceImpl<T, K, PageBounds, PageList<T>, List<T>> {
    private static final String DEFAULT_ORDER_FIELD = "create_at";

    @Override
    public Msg<PageList<T>> fuzzySearch(T record) {
        PageList<Order> orders = new PageList<>();
        orders.add(new Order(DEFAULT_ORDER_FIELD, Order.Direction.DESC, ""));

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
            orders.add(new Order(DEFAULT_ORDER_FIELD, Order.Direction.DESC, ""));
            pageBounds.setOrders(orders);
        }
        PageHelper.startPage(pageBounds.getPage(), pageBounds.getLimit());
        PageHelper.orderBy(pageBounds.getOrdersString());
        List<T> result = getRepositoryDao().fuzzySearch(record);
        if (result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }
        long total = ((Page) result).getTotal();
        PageList<T> pageList = new PageList<>(result);
        pageList.setPaginator(pageBounds.getPage(), pageBounds.getPage(), Math.toIntExact(total));
        msg.setData(pageList);

        return msg;
    }

    @Override
    public Msg<PageList<T>> selectAllByExample(T record) {
        PageList<Order> orders = new PageList<>();
        orders.add(new Order(DEFAULT_ORDER_FIELD, Order.Direction.DESC, ""));
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
        PageHelper.startPage(pageBounds.getPage(), pageBounds.getLimit());
        PageHelper.orderBy(pageBounds.getOrdersString());

        List<T> result = getRepositoryDao().selectByExample(record, false);
        if (result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }
        long total = ((Page) result).getTotal();
        PageList<T> pageList = new PageList<>(result);
        pageList.setPaginator(pageBounds.getPage(), pageBounds.getPage(), Math.toIntExact(total));
        msg.setData(pageList);
        return msg;
    }
}
