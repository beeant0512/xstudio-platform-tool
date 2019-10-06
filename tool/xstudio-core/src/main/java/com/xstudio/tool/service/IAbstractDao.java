package com.xstudio.tool.service;

import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/5/17
 */
public interface IAbstractDao<T, K, P, M extends List<T>> {
    /**
     * 统计总数
     *
     * @param record 统计条件
     * @return 统计结果
     */
    Long countByExample(T record);

    /**
     * 插入设置的值
     *
     * @param record 对象
     * @return 插入成功数
     */
    int insertSelective(T record);

    /**
     * 批量插入设置的值
     *
     * @param record 对象
     * @return 插入成功数
     */
    int batchInsertSelective(List<T> record);

    /**
     * 主键删除
     *
     * @param key 主键
     * @return 删除条数
     */
    int deleteByPrimaryKey(K key);

    /**
     * 批量删除
     *
     * @param keys 主键
     * @return 删除条数
     */
    int batchDeleteByPrimaryKey(List<K> keys);

    /**
     * 更新选定的值
     *
     * @param record 对象
     * @return 更新的条数
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 按条件更新选定的值
     *
     * @param example 条件
     * @param record  值对象
     * @return 更新的条数
     */
    int updateByExampleSelective(T example, T record);

    /**
     * 批量按主键更新设定的值
     *
     * @param record 值
     * @return 更新的条数
     */
    int batchUpdateByPrimaryKeySelective(List<T> record);

    /**
     * 按主键获取
     *
     * @param key 主键
     * @return 对象
     */
    T selectByPrimaryKey(K key);

    /**
     * 按条件获取（不含大字段）
     *
     * @param record   条件
     * @param distinct 是否distinct
     * @return 对象list
     */
    M selectByExample(T record, boolean distinct);

    /**
     * 按条件获取（含大字段）
     *
     * @param record   条件
     * @param distinct 是否distinct
     * @return 对象list
     */
    M selectByExampleWithBLOBs(T record, boolean distinct);

    /**
     * 分页获取
     *
     * @param record     条件
     * @param pageBounds 分页参数
     * @return 对象list
     */
    M selectByExampleByPager(T record, P pageBounds);

    /**
     * 模糊搜索
     *
     * @param record 搜索对象
     * @return 满足条件的对象
     */
    M fuzzySearch(T record);

    /**
     * 分页模糊搜索
     *
     * @param record     对象
     * @param pageBounds 分页参数
     * @return 对象list
     */
    M fuzzySearchByPager(T record, P pageBounds);
}
