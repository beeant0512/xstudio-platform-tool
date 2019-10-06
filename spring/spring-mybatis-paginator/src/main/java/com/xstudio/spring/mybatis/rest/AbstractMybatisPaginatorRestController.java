package com.xstudio.spring.mybatis.rest;

import com.xstudio.tool.enums.EnError;
import com.xstudio.spring.mybatis.ant.PageRequest;
import com.xstudio.spring.mybatis.ant.PageResponse;
import com.xstudio.spring.web.rest.AbstractBaseRestController;
import com.xstudio.tool.utils.BaseModelObject;
import com.xstudio.tool.utils.Msg;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaobiao
 * @version 2019/5/20
 */
public abstract class AbstractMybatisPaginatorRestController<Target extends BaseModelObject, Key> extends AbstractBaseRestController<Target, Key> {

    @GetMapping(value = {"table"})
    public Msg<PageResponse<Target>> table(Target object, PageRequest pageRequest, HttpServletRequest request, HttpServletResponse response) {
        Msg<PageResponse<Target>> msg = new Msg<>();
        PageRequest webRequest = new PageRequest(pageRequest);

        Msg<PageList<Target>> list = getService().fuzzySearchByPager(object, webRequest.getPageBounds());
        if (!list.getSuccess()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        PageResponse<Target> pageResponse = new PageResponse<>();
        pageResponse.setList(list.getData());
        msg.setData(pageResponse);
        return msg;
    }
}
