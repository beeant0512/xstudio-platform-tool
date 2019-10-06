package com.xstudio.spring.mybatis.pagehelper;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.xstudio.tool.service.IAbstractDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/5/18
 */
public interface IMybatisPageHelperDao<Target, Key> extends IAbstractDao<Target, Key, PageBounds, List<Target>> {
    /**
     * 统计总数
     *
     * @param record 统计条件
     * @return 统计结果
     */
    @Override
    Long countByExample(@Param("example") Target record);

    @Override
    Target selectByPrimaryKey(@Param("key") Key key);

    @Override
    int deleteByPrimaryKey(@Param("key") Key key);

    /**
     * 批量删除
     *
     * @param keys 主键
     * @return 删除条数
     */
    @Override
    int batchDeleteByPrimaryKey(@Param("items") List<Key> keys);

    /**
     * 按条件更新选定的值
     *
     * @param example 条件
     * @param record  值对象
     * @return 更新的条数
     */
    @Override
    int updateByExampleSelective(@Param("example") Target example, @Param("record") Target record);

    /**
     * 按条件获取（不含大字段）
     *
     * @param record   条件
     * @param distinct 是否distinct
     * @return 对象list
     */
    @Override
    List<Target> selectByExample(@Param("example") Target record, @Param("distinct") boolean distinct);

    /**
     * 按条件获取（含大字段）
     *
     * @param record   条件
     * @param distinct 是否distinct
     * @return 对象list
     */
    @Override
    List<Target> selectByExampleWithBLOBs(@Param("example") Target record, @Param("distinct") boolean distinct);

    /**
     * 分页获取
     *
     * @param record     条件
     * @param pageBounds 分页参数
     * @return 对象list
     */
    @Override
    List<Target> selectByExampleByPager(@Param("example") Target record, @Param("pageBounds") PageBounds pageBounds);

    /**
     * 分页模糊搜索
     *
     * @param record     对象
     * @param pageBounds 分页参数
     * @return 对象list
     */
    @Override
    List<Target> fuzzySearchByPager(@Param("example") Target record, @Param("pageBounds") PageBounds pageBounds);
}
