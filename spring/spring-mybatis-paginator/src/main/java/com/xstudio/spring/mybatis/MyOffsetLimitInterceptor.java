package com.xstudio.spring.mybatis;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.*;

/**
 * @author xiaobiao
 * @version 2019/5/18
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
public class MyOffsetLimitInterceptor extends com.github.miemiedev.mybatis.paginator.OffsetLimitInterceptor {
    static int MAPPED_STATEMENT_INDEX = 0;
    static int ROWBOUNDS_INDEX = 2;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
        RowBounds rowBounds = (RowBounds)queryArgs[ROWBOUNDS_INDEX];
        final PageBounds pageBounds = new PageBounds(rowBounds);
        List orders = pageBounds.getOrders();
        if(orders != null && !orders.isEmpty()) {
            Map dialect = this.getEntityPropertiesMap(ms);
            if(dialect != null) {
                ArrayList boundSql = new ArrayList();

                for(int async = 0; async < orders.size(); ++async) {
                    Order listFuture = (Order)orders.get(async);
                    Order countTask = Order.create(listFuture.getProperty(), listFuture.getDirection().toString(), listFuture.getOrderExpr());
                    String countFutrue = listFuture.getProperty();
                    if(countFutrue != null && (countFutrue = countFutrue.toLowerCase()).length() > 0 && dialect.containsKey(countFutrue)) {
                        countTask.setProperty((String)dialect.get(countFutrue));
                    }

                    boundSql.add(countTask);
                }

                if(!boundSql.isEmpty()) {
                    pageBounds.setOrders(boundSql);
                }
                queryArgs[ROWBOUNDS_INDEX] = pageBounds;
            }
        }
        return super.intercept(invocation);
    }

    public Map<String, String> getEntityPropertiesMap(MappedStatement ms) {
        if(ms != null) {
            List rmList = ms.getResultMaps();
            if(rmList != null && !rmList.isEmpty()) {
                HashMap propColumnMap = new HashMap();

                for(int i = 0; i < rmList.size(); ++i) {
                    ResultMap rm = (ResultMap)rmList.get(i);
                    if(rm != null) {
                        List ps = rm.getPropertyResultMappings();
                        if(ps != null && !ps.isEmpty()) {
                            Iterator i$ = ps.iterator();

                            while(i$.hasNext()) {
                                ResultMapping p = (ResultMapping)i$.next();
                                if(p.getColumn() != null){
                                    propColumnMap.put(p.getProperty().toLowerCase(), p.getColumn().toLowerCase());
                                }
                            }
                        }
                    }
                }

                if(!propColumnMap.isEmpty()) {
                    return propColumnMap;
                }
            }
        }

        return null;
    }
}
