package com.xstudio.srping.cache;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.xstudio.spring.core.ContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 *
 * @author xiaobiao
 * @date 2018/11/29
 */
public class CacheHelper {

    private static Logger logger = LoggerFactory.getLogger(CacheHelper.class);

    private static JCacheCacheManager jCacheCacheManager;

    private static RedisUtils redisTemplate;

    private static RedisConnectionFactory redisConnectionFactory;

    private static boolean clusterMode = false;

    private CacheHelper() {
    }

    /**
     * Getter for property 'jCacheCacheManager'.
     *
     * @return Value for property 'jCacheCacheManager'.
     */
    private static JCacheCacheManager getJCacheCacheManager() {
        if (null == jCacheCacheManager) {
            jCacheCacheManager = ContextUtil.getBean(JCacheCacheManager.class);
        }
        return jCacheCacheManager;
    }

    /**
     * Getter for property 'redisTemplate'.
     *
     * @return Value for property 'redisTemplate'.
     */
    private static RedisUtils getRedisUtils() {
        if (null == redisTemplate) {
            redisTemplate = ContextUtil.getBean(RedisUtils.class);
        }
        return redisTemplate;
    }

    /**
     * 设置本地缓存
     *
     * @param cacheName 缓存名称
     * @param key       Key
     * @param value     值
     */
    public static void putNative(String cacheName, String key, Object value) {
        Cache cache = getJCacheCacheManager().getCache(cacheName);
        if (null == cache) {
            logger.warn("请先配置 {} 缓存名称", cacheName);
            return;
        }
        cache.put(key, value);
    }

    /**
     * 设置本地缓存
     *
     * @param cacheName 缓存名称
     * @param key       Key
     * @param value     值
     * @see CacheHelper#putNative(String, String, Object)
     * @deprecated
     */
    public static void putNative(String cacheName, String key, Object value, Integer expireSeconds) {
        String programCacheName = "PROGRAMING_CACHE" + expireSeconds;
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        javax.cache.Cache<Object, Object> javaxCache = cacheManager.getCache(programCacheName);
        if (null == javaxCache) {
            cacheManager.createCache(programCacheName,
                    new MutableConfiguration<Object, Object>()
                            .setTypes(Object.class, Object.class)
                            .setStoreByValue(false)
                            .setStatisticsEnabled(true)
                            .setExpiryPolicyFactory(FactoryBuilder.factoryOf(new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, expireSeconds)))));
            javaxCache = cacheManager.getCache(programCacheName, Object.class, Object.class);
        }

        if (null == javaxCache) {
            logger.warn("请先配置 {} 缓存名称", cacheName);
            return;
        }
        javaxCache.put(key, value);
    }

    /**
     * 获取本地缓存
     *
     * @param cacheName 缓存名称
     * @param key       Key
     * @return Object  值
     */
    public static Object getNative(String cacheName, String key) {
        Cache cache = getJCacheCacheManager().getCache(cacheName);
        if (null == cache) {
            return null;
        }

        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (null == valueWrapper) {
            return null;
        }

        return valueWrapper.get();
    }

    /**
     * 获取本地缓存
     *
     * @param key Key
     * @return Object  值
     * @see CacheHelper#getNative(String, String)
     * @deprecated
     */
    public static Object getNative(String key) {
        String programCacheName = "PROGRAMING_CACHE";
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        Iterable<String> cacheNames = cacheManager.getCacheNames();
        javax.cache.Cache<Object, Object> cache;
        Object value;
        for (String cacheName : cacheNames) {
            if (!cacheName.startsWith(programCacheName)) {
                continue;
            }
            cache = cacheManager.getCache(cacheName);
            value = cache.get(key);
            if (null != value) {
                return value;
            }
        }

        return null;
    }

    /**
     * 清除缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     */
    public static void expireNative(String cacheName, String key) {
        Cache cache = getJCacheCacheManager().getCache(cacheName);
        if (null == cache) {
            return;
        }

        cache.evict(key);
    }

    /**
     * 从redis获取缓存
     *
     * @param key Key
     * @return String
     */
    public static String getRemote(String key) {
        if (clusterMode) {
            // todo
            return "";
        }
        return getRedisUtils().get(key);
    }

    /**
     * 根据key获取对象
     */
    public static <T> T getRemote(final String key, Class<T> clazz, Feature... features) {
        if (clusterMode) {
            // todo
            return null;
        }
        return getRedisUtils().get(key, clazz, features);
    }

    /**
     * @param key
     * @param type
     * @param features
     * @return
     * @see CacheHelper#getRemote(String, Type, Feature...)
     * @deprecated
     */
    public static <T> T getRemote(final String key, TypeReference<T> type, Feature... features) {
        if (clusterMode) {
            // todo
            return null;
        }
        return getRedisUtils().get(key, type, features);
    }

    /**
     * @param key
     * @param type
     * @param features
     * @return
     */
    public static <T> T getRemote(final String key, Type type, Feature... features) {
        if (clusterMode) {
            // todo
            return null;
        }
        return getRedisUtils().get(key, type, features);
    }

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the list
     */
    @SuppressWarnings("unchecked")
    public static List<String> getRemoteList(String key, int start, int end) {
        if (clusterMode) {
            // todo
            return null;
        }
        return getRedisUtils().getRedisTemplate().opsForList().range(key, start, end);
    }

    /**
     * 添加到带有 过期时间的  缓存
     *
     * @param key  redis主键
     * @param time 过期时间 单位： 秒
     */
    public static void expireRemote(final String key, final long time) {
        if (clusterMode) {
            // todo
        }
        getRedisUtils().expire(key, time);
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean existsRemote(final String key) {
        if (clusterMode) {
            // todo
            return true;
        }
        return getRedisUtils().exists(key);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public static void removeRemote(final String... keys) {
        if (clusterMode) {
            // todo
        }
        getRedisUtils().remove(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    public static void removeRemote(final String key) {
        if (clusterMode) {
            // todo
        }
        getRedisUtils().getRedisTemplate().delete(key);
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    @SuppressWarnings("unchecked")
    public static void removeRemotePattern(final String pattern) {
        if (clusterMode) {
            // todo
        }
        getRedisUtils().removePattern(pattern);
    }

    /**
     * redis List数据结构 : 根据参数 i 的值，移除列表中与参数 value 相等的元素
     *
     * @param key   the key
     * @param i     the
     * @param value the value
     */
    public static Long removeListRemote(String key, long i, String value) {
        if (clusterMode) {
            // todo
            return null;
        }
        return getRedisUtils().getRedisTemplate().opsForList().remove(key, i, value);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean setRemote(final String key, Object value) {
        if (clusterMode) {
            // todo
            return false;
        }
        return getRedisUtils().set(key, value);
    }

    /**
     * 写入缓存
     *
     * @param key           缓存键
     * @param value         缓存值
     * @param expireSeconds 过期时间，单位: 秒
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean setRemote(final String key, Object value, Long expireSeconds) {
        if (clusterMode) {
            // todo
            return false;
        }
        return getRedisUtils().set(key, value, expireSeconds);
    }

    /**
     * 写入缓存
     *
     * @param key  缓存键
     * @param list 缓存值
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Long leftPushAllRemote(String key, List<String> list) {
        return getRedisUtils().leftPushAll(key, list);
    }


    /**
     * 查询在以keyPatten的所有  key
     *
     * @param keyPatten the key patten
     * @return the set
     */
    public static Set<String> keysRemote(final String keyPatten) {
        if (clusterMode) {
            // todo
            return null;
        }
        return getRedisUtils().keys(keyPatten);
    }

    /**
     * redis List数据结构 : 返回列表 key 的长度 ; 如果 key 不存在，则 key 被解释为一个空列表，返回 0 ; 如果 key 不是列表类型，返回一个错误。
     *
     * @param key the key
     * @return the long
     */
    @SuppressWarnings("unchecked")
    public static Long lengthRemote(String key) {
        if (clusterMode) {
            // todo
            return null;
        }
        return getRedisUtils().length(key);
    }
}
