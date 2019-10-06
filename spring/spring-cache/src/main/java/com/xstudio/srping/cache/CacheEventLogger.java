package com.xstudio.srping.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiaobiao
 * @version 2018/9/17
 */
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    private static final Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);

    @Override
    public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
        if (logger.isTraceEnabled()) {
            logger.trace("cache event: " + event.getType() + ", Key: " + event.getKey()
                    + ", old value: " + event.getOldValue()
                    + ", new value: " + event.getNewValue());
        }
    }
}
