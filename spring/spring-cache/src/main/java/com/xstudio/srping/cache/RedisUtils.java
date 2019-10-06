package com.xstudio.srping.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.xstudio.srping.cache.exception.RedisWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaobiao
 * @version 2017/11/21
 */
@ConditionalOnClass(RedisTemplate.class)
@Component
public class RedisUtils {

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加到带有 过期时间的  缓存
     *
     * @param key  缓存键  redis主键
     * @param time 过期时间 单位： 秒
     */
    public void expire(final String key, final long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key 缓存键
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Getter for property 'redisTemplate'.
     *
     * @return Value for property 'redisTemplate'.
     */
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key   缓存键   the key
     * @param start the start
     * @param end   the end
     * @return the list
     */
    @SuppressWarnings("unchecked")
    public List<String> getList(String key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 根据key获取对象
     *
     * @param key   缓存键
     * @param clazz
     * @return
     */
    public <T> T get(final String key, Class<T> clazz, Feature... features) {
        Object value = get(key);
        if (null == value) {
            return null;
        }
        if (value instanceof String) {
            return JSON.parseObject((String) value, clazz, features);
        }
        String s = JSON.toJSONString(value);
        return JSON.parseObject(s, clazz, features);
    }

    /**
     * 获取redis缓存的对象
     *
     * @param key      缓存键
     * @param type     缓存对象类型
     * @param features fastjson特性
     * @return
     */
    public <T> T get(String key, Type type, Feature... features) {
        Object value = get(key);
        if (null == value) {
            return null;
        }
        if (value instanceof String) {
            return JSON.parseObject((String) value, type, features);
        }
        String s = JSON.toJSONString(value);
        return JSON.parseObject(s, type, features);
    }


    /**
     * 获取redis缓存的对象
     *
     * @param key      缓存键
     * @param type     缓存对象类型
     * @param features fastjson特性
     * @return
     * @see RedisUtils#get(String, Type, Feature...)
     * @deprecated
     */
    public <T> T get(String key, TypeReference<T> type, Feature... features) {
        return get(key, type.getType(), features);
    }


    /**
     * 读取缓存 返回类型 String
     * <p>
     * 由于配置的是 FastJsonRedisSerializer<>(String.class)
     *
     * @param key 缓存键
     * @return
     * @see RedisTemplate
     * @see RedisUtils#get(String, Class, Feature...)
     */
    @SuppressWarnings("unchecked")
    public String get(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return (String) operations.get(key);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys 缓存键
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key 缓存键
     */
    @SuppressWarnings("unchecked")
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    @SuppressWarnings("unchecked")
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * redis List数据结构 : 根据参数 i 的值，移除列表中与参数 value 相等的元素
     *
     * @param key   缓存键   the key
     * @param i     the
     * @param value the value
     */
    public Long removeList(String key, long i, String value) {
        return redisTemplate.opsForList().remove(key, i, value);
    }

    /**
     * 写入缓存
     *
     * @param key   缓存键
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            throw new RedisWriteException("redis写入失败");
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key   缓存键
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            throw new RedisWriteException("redis写入失败");
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key  缓存键
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public Long leftPushAll(String key, List<String> list) {
        Long pushNumber = 0L;
        try {
            pushNumber = redisTemplate.opsForList().leftPushAll(key, list);
        } catch (Exception e) {
            logger.error("redis写入失败", e);
        }
        return pushNumber;
    }


    /**
     * 查询在以keyPatten的所有  key
     *
     * @param keyPatten 缓存键Patten
     * @return the set
     */
    public Set<String> keys(final String keyPatten) {
        return redisTemplate.keys(keyPatten + "*");
    }

    /**
     * redis List数据结构 : 返回列表 key 的长度 ; 如果 key 不存在，则 key 被解释为一个空列表，返回 0 ; 如果 key 不是列表类型，返回一个错误。
     *
     * @param key 缓存键 the key
     * @return the long
     */
    @SuppressWarnings("unchecked")
    public Long length(String key) {
        return redisTemplate.opsForList().size(key);
    }
}
