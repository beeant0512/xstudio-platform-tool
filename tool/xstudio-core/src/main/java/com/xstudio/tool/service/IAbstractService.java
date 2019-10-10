package com.xstudio.tool.service;


import com.xstudio.tool.utils.Msg;

import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/5/17
 */
@SuppressWarnings("unused")
public interface IAbstractService<T, K, P, L extends List<T>, D extends List<T>> {
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
    Msg<T> insertSelective(T record);

    /**
     * 批量插入设置的值
     *
     * @param record 对象
     * @return 插入成功数
     */
    Msg<L> batchInsertSelective(L record);

    /**
     * 主键删除
     *
     * @param keys 主键
     * @return 删除条数
     */
    Msg<Integer> deleteByPrimaryKey(K keys);

    /**
     * 批量删除
     *
     * @param keys 主键
     * @return 删除条数
     */
    Msg<Integer> batchDeleteByPrimaryKey(List<K> keys);

    /**
     * 更新选定的值
     *
     * @param record 对象
     * @return 更新的条数
     */
    Msg<T> updateByPrimaryKeySelective(T record);

    /**
     * 按条件更新选定的值
     *
     * @param example 条件
     * @param record  值对象
     * @return 更新的条数
     */
    Msg<T> updateByExampleSelective(T example, T record);

    /**
     * 批量按主键更新设定的值
     *
     * @param record 值
     * @return 更新的条数
     */
    Msg<L> batchUpdateByPrimaryKeySelective(L record);

    /**
     * 按主键获取
     *
     * @param keys 主键
     * @return 对象
     */
    Msg<T> selectByPrimaryKey(K keys);

    /**
     * 按条件获取（不含大字段）
     *
     * @param record 条件
     * @param orders 排序
     * @return 对象list
     */
    Msg<T> selectOneByExample(T record, List<?> orders);

    /**
     * 按条件获取（不含大字段） distinct = true
     *
     * @param record 条件
     * @return 对象list
     */
    Msg<T> selectOneByExample(T record);

    /**
     * 按条件获取（含大字段）
     *
     * @param record 条件
     * @param orders 排序
     * @return 对象list
     */
    Msg<T> selectOneByExampleWithBlobs(T record, List<?> orders);

    /**
     * 按条件获取（含大字段）
     *
     * @param record 条件
     * @return 对象list
     */
    Msg<L> selectAllByExample(T record);

    /**
     * 按条件获取（含大字段），指定排序
     *
     * @param record 条件
     * @param orders 排序
     * @return 对象list
     */
    Msg<L> selectAllByExample(T record, List<?> orders);

    /**
     * 按条件获取（含大字段），指定排序
     *
     * @param record 条件
     * @param orders 排序
     * @return 对象list
     */
    Msg<L> selectAllByExampleWithBlobs(T record, List<?> orders);

    /**
     * 分页获取
     *
     * @param record     条件
     * @param pageBounds 分页参数
     * @return 对象list
     */
    Msg<L> selectByExampleByPager(T record, P pageBounds);

    /**
     * 分页获取(含大字段）
     *
     * @param record     条件
     * @param pageBounds 分页参数
     * @return 对象list
     */
    Msg<L> selectByExampleWithBlobsByPager(T record, P pageBounds);

    /**
     * 模糊搜索
     *
     * @param record 搜索对象
     * @return 满足条件的对象
     */
    Msg<L> fuzzySearch(T record);

    /**
     * 分页模糊搜索
     *
     * @param record     对象
     * @param pageBounds 分页参数
     * @return 对象list
     */
    Msg<L> fuzzySearchByPager(T record, P pageBounds);

    /**
     * 获取dao实现
     *
     * @return Dao实现
     */
    IAbstractDao<T, K, P, D> getRepositoryDao();

    /**
     * 设置默认值
     *
     * @param record 对象
     */
    void setDefaults(T record);

    /**
     * 唯一验证
     *
     * @param record 待验证对象字段
     * @return 验证结果 msg.code == 0 时表示该字段可用
     */
    Msg<String> uniqueValid(T record);

    /**
     * 设置创建时的默认值
     *
     * @param record 对象
     */
    void setCreateInfo(T record);

    /**
     * 设置更新时的默认值
     *
     * @param record 对象
     */
    void setUpdateInfo(T record);
}
