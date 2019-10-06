package com.xstudio.spring.mybatis.paginator;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.tool.service.IAbstractDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/5/18
 */
public interface IMybatisPaginatorDao<T, K> extends IAbstractDao<T, K, PageBounds, PageList<T>> {
    /**
     * 统计总数
     *
     * @param record 统计条件
     * @return 统计结果
     */
    @Override
    Long countByExample(@Param("example") T record);

    /**
     * 主键获取
     *
     * @param key 主键
     * @return
     */
    @Override
    T selectByPrimaryKey(@Param("key") K key);

    /**
     * 主键删除
     *
     * @param key 主键
     * @return
     */
    @Override
    int deleteByPrimaryKey(@Param("key") K key);

    /**
     * 批量删除
     *
     * @param keys 主键
     * @return 删除条数
     */
    @Override
    int batchDeleteByPrimaryKey(@Param("items") List<K> keys);

    /**
     * 按条件更新选定的值
     *
     * @param example 条件
     * @param record  值对象
     * @return 更新的条数
     */
    @Override
    int updateByExampleSelective(@Param("example") T example, @Param("record") T record);

    /**
     * 按条件获取（不含大字段）
     *
     * @param record   条件
     * @param distinct 是否distinct
     * @return 对象list
     */
    @Override
    PageList<T> selectByExample(@Param("example") T record, @Param("distinct") boolean distinct);

    /**
     * 按条件获取（含大字段）
     *
     * @param record   条件
     * @param distinct 是否distinct
     * @return 对象list
     */
    @Override
    PageList<T> selectByExampleWithBLOBs(@Param("example") T record, @Param("distinct") boolean distinct);

    /**
     * 分页获取
     *
     * @param record     条件
     * @param pageBounds 分页参数
     * @return 对象list
     */
    @Override
    PageList<T> selectByExampleByPager(@Param("example") T record, @Param("pageBounds") PageBounds pageBounds);

    /**
     * 分页模糊搜索
     *
     * @param record     对象
     * @param pageBounds 分页参数
     * @return 对象list
     */
    @Override
    PageList<T> fuzzySearchByPager(@Param("example") T record, @Param("pageBounds") PageBounds pageBounds);
}
