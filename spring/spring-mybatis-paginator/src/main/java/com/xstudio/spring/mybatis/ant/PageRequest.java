package com.xstudio.spring.mybatis.ant;

import com.xstudio.tool.web.Request;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ant.design 表格组件请求参数对象
 *
 * @author xiaobiao
 * @version 1
 * @date 2017/11/4
 * @see <a href="https://ant.design/components/table-cn/">Table</a>
 */
public class PageRequest extends Request implements Serializable {
    private static final long serialVersionUID = -3783512820125403682L;

    public PageRequest() {
    }

    public PageRequest(Request request) {
        this.setCurrentPage(request.getCurrentPage());
        this.setPageSize(request.getPageSize());
        this.setSorter(request.getSorter());
    }

    public PageBounds getPageBounds() {
        PageBounds pageBounds = new PageBounds(getCurrentPage(), getPageSize(), true);
        if (!"".equals(getSorter())) {
            pageBounds.setOrders(getOrder());
        }
        return pageBounds;
    }

    private List<Order> getOrder() {
        List<Order> orders = new ArrayList<>();
        String[] split = getSorter().split("_");
        Order.Direction direction = Order.Direction.ASC;
        if ("descend".equals(split[1])) {
            direction = Order.Direction.DESC;
        }
        orders.add(new Order(split[0], direction, ""));

        return orders;
    }
}
