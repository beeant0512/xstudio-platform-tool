package com.xstudio.spring.mybatis.paginator;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.tool.service.IAbstractService;
import com.xstudio.tool.utils.BaseModelObject;

import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/5/20
 */
public interface IMybatisPaginatorService<T extends BaseModelObject, K> extends IAbstractService<T, K, PageBounds, PageList<T>, List<T>> {
}
