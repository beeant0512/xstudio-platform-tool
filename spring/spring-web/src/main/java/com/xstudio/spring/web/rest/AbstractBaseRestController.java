package com.xstudio.spring.web.rest;


import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.service.IAbstractService;
import com.xstudio.tool.utils.BaseModelObject;
import com.xstudio.tool.utils.Msg;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基础控制器服务 针对application/json传参
 *
 * @author xiaobiao
 * @version 2019/5/20
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBaseRestController<Target extends BaseModelObject, Key> {

    /**
     * 获取服务
     *
     * @return 抽象IAbstractService
     */
    public abstract IAbstractService getService();


    /**
     * 通过组件获取详情
     *
     * @param id       主键
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @GetMapping(value = "/{id}")
    public Msg<Target> get(@PathVariable(name = "id") Key id, HttpServletRequest request, HttpServletResponse response) {
        return getService().selectByPrimaryKey(id);
    }

    /**
     * 通过组件获取详情
     *
     * @param object   对象主键字段必须赋值
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @GetMapping(value = "/get")
    public Msg<Target> get(Target object, HttpServletRequest request, HttpServletResponse response) {
        if (null == object.valueOfKey() || "".equals(object.valueOfKey())) {
            return new Msg<>(EnError.EMPTY_PARAM);
        }
        return getService().selectByPrimaryKey(object.valueOfKey());
    }

    /**
     * 通过主键删除记录
     *
     * @param id       主键
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @DeleteMapping(value = "/{id}")
    public Msg<Boolean> delete(@PathVariable(name = "id") Key id, HttpServletRequest request, HttpServletResponse response) {
        return getService().deleteByPrimaryKey(id);
    }

    /**
     * 通过主键删除记录
     *
     * @param object 对象 主键字段必须给值或赋值key字段
     * @return Msg&lt;Integer&gt;
     */
    @PostMapping(value = "delete")
    public Msg<Integer> delete(Target object) {
        if (object.valueOfKey() == null || "".equals(object.valueOfKey())) {
            return new Msg<>(EnError.EMPTY_PARAM);
        }
        return getService().deleteByPrimaryKey(object.valueOfKey());
    }

    /**
     * 写入一条数据
     *
     * @param record   写入对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @PostMapping(value = "")
    public Msg<Target> post(Target record, HttpServletRequest request, HttpServletResponse response) {
        return getService().insertSelective(record);
    }

    /**
     * 写入一条数据
     *
     * @param record   写入对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @PostMapping(value = "add")
    public Msg<Target> add(Target record, HttpServletRequest request, HttpServletResponse response) {
        return getService().insertSelective(record);
    }

    /**
     * 更新一条数据
     *
     * @param record   对象
     * @param id       主键
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @PutMapping(value = "/{id}")
    public Msg<Target> put(Target record, @PathVariable(name = "id") Key id, HttpServletRequest request, HttpServletResponse response) {
        record.assignKeyValue(id);
        return getService().updateByPrimaryKeySelective(record);
    }

    /**
     * 更新一条数据
     *
     * @param record   对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg&lt;Target&gt;
     */
    @PostMapping(value = "/update")
    public Msg<Target> update(Target record, HttpServletRequest request, HttpServletResponse response) {
        return getService().updateByPrimaryKeySelective(record);
    }

    /**
     * 唯一性校验
     *
     * @param object  对象
     * @param request HttpServletRequest
     * @return Msg&lt;Target&gt;
     */
    @PostMapping(value = "/validate")
    public Msg<Target> validate(Target object, HttpServletRequest request) {
        return getService().uniqueValid(object);
    }
}
