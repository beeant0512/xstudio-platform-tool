package com.xstudio.tool.service;

import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.utils.BaseModelObject;
import com.xstudio.tool.utils.Msg;
import org.apache.commons.collections4.ListUtils;

import java.util.Date;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2019/5/20
 */
public abstract class AbstractServiceImpl<T extends BaseModelObject, K, P, L extends List<T>, M extends List<T>> implements IAbstractService<T, K, P, L, M> {
    private static int batchPerSqlNumber = 100;


    @Override
    public Msg<String> uniqueValid(T record) {
        Msg<String> msg = new Msg<>();
        Object key = record.valueOfKey();
        if (null == key) {
            Long existNumber = getRepositoryDao().countByExample(record);
            if (existNumber > 0) {
                msg.setResult(EnError.CONFLICT);
                return msg;
            }

            return msg;
        }

        /* 主键存在 参数获取的对象主键不一致时 返回错误 */
        record.assignKeyValue(null);

        M dbRecord = (M) getRepositoryDao().selectByExample(record, true);
        if (null != dbRecord && !dbRecord.isEmpty() && !key.equals(dbRecord.get(0).valueOfKey())) {
            msg.setResult(EnError.CONFLICT);
            return msg;
        }

        return msg;
    }

    @Override
    public Long countByExample(T record) {
        return getRepositoryDao().countByExample(record);
    }


    @Override
    public Msg<T> insertSelective(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setCreateInfo(record);
        int result = getRepositoryDao().insertSelective(record);
        boolean failed = isFailed(msg, 0 == result, 101, "写入失败");
        if (failed) {
            return msg;
        }

        msg.setData(record);
        return msg;
    }

    /**
     * 是否数据库操作失败
     *
     * @param msg       返回消息对象
     * @param isFailed  是否失败标志位
     * @param errorCode 返回的错误码
     * @param message   返回的错误消息
     * @return 是否失败
     */
    private boolean isFailed(Msg<T> msg, boolean isFailed, int errorCode, String message) {
        if (isFailed) {
            msg.setCode(errorCode);
            msg.setMsg(message);
            return true;
        }
        return false;
    }

    @Override
    public Msg<L> batchInsertSelective(L records) {
        Msg<L> msg = new Msg<>();
        if (null == records || records.isEmpty()) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }
        for (T record : records) {
            setDefaults(record);
            setCreateInfo(record);
        }

        int insertCount = 0;
        List<List<T>> lists = ListUtils.partition(records, batchPerSqlNumber);
        for (List<T> list : lists) {
            insertCount = insertCount + getRepositoryDao().batchInsertSelective(list);
        }

        if (0 == insertCount) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }
        msg.setData(records);

        return msg;
    }

    @Override
    public Msg<Integer> deleteByPrimaryKey(K keys) {
        Msg<Integer> msg = new Msg<>();
        int result = getRepositoryDao().deleteByPrimaryKey(keys);
        if (0 == result) {
            msg.setResult(EnError.DELETE_NONE);
            return msg;
        }

        return msg;
    }

    @Override
    public Msg<Integer> batchDeleteByPrimaryKey(List<K> keys) {
        Msg<Integer> msg = new Msg<>();
        if (null == keys || keys.isEmpty()) {
            msg.setResult(EnError.EMPTY_PARAM);
            return msg;
        }
        int result = getRepositoryDao().batchDeleteByPrimaryKey(keys);
        if (0 == result) {
            msg.setResult(EnError.DELETE_NONE);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    @Override
    public Msg<T> updateByPrimaryKeySelective(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByPrimaryKeySelective(record);
        if (isFailed(msg, 0 == result, 102, "更新失败")) {
            return msg;
        }

        // 重新获取数据
        T dbRecord = (T) getRepositoryDao().selectByPrimaryKey(record.valueOfKey());
        msg.setData(dbRecord);
        return msg;
    }

    @Override
    public Msg<T> updateByExampleSelective(T example, T record) {
        Msg<T> msg = new Msg<>();
        K keyValue = (K) record.valueOfKey();
        setDefaults(record);
        setUpdateInfo(record);
        record.assignKeyValue(null);
        int result = getRepositoryDao().updateByExampleSelective(example, record);
        if (isFailed(msg, 0 == result, 102, "更新失败")) {
            return msg;
        }

        // 重新获取数据
        if (null != keyValue) {
            T dbRecord = (T) getRepositoryDao().selectByPrimaryKey(keyValue);
            msg.setData(dbRecord);
        }
        return msg;
    }

    @Override
    public Msg<L> batchUpdateByPrimaryKeySelective(L records) {
        Msg<L> msg = new Msg<>();
        if (null == records || records.isEmpty()) {
            msg.setResult(EnError.EMPTY_PARAM);
            return msg;
        }
        for (T record : records) {
            setDefaults(record);
            setUpdateInfo(record);
        }

        int count = 0;
        List<List<T>> lists = ListUtils.partition(records, batchPerSqlNumber);
        for (List<T> list : lists) {
            count = count + getRepositoryDao().batchUpdateByPrimaryKeySelective(list);
        }

        if (0 == count) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        msg.setData(records);
        return msg;
    }

    @Override
    public Msg<T> selectByPrimaryKey(K keys) {
        Msg<T> msg = new Msg<>();
        T result = (T) getRepositoryDao().selectByPrimaryKey(keys);
        if (isFailed(msg, null == result, 100, "没有匹配项")) {
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    @Override
    public abstract IAbstractDao getRepositoryDao();

    /**
     * 设置默认值
     *
     * @param record 对象
     */
    @Override
    public abstract void setDefaults(T record);

    @Override
    public void setCreateInfo(T record) {
        Date now = new Date();
        if (null == record.getCreateAt()) {
            record.setCreateAt(now);
        }
        record.setUpdateAt(now);
        if (null == record.getCreateBy()) {
            record.setCreateBy(getActorId(record));
        }
    }

    @Override
    public void setUpdateInfo(T record) {
        record.setUpdateAt(new Date());
        record.setUpdateBy(getActorId(record));
    }

    /**
     * 获取当前用户
     *
     * @param record 对象
     * @return 当前用户ID
     */
    public abstract String getActorId(T record);
}