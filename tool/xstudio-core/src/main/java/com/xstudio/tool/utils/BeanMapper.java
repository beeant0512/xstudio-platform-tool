package com.xstudio.tool.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2018/9/26
 */
public class BeanMapper {

    private static Logger logger = LogManager.getLogger(BeanMapper.class);

    public BeanMapper() {
    }

    /**
     * 对象数据转换
     *
     * @param source           原对象
     * @param destinationClass 目标对象class类
     * @param keepKeyField     是否保留BaseModelObject的key字段
     * @param <T>              返回类型
     * @return 目标对象
     */
    public static <T> T map(Object source, Class<T> destinationClass, Boolean keepKeyField) {
        if (!keepKeyField) {
            return map(source, destinationClass);
        }
        return JSON.parseObject(JSON.toJSONString(source), destinationClass);
    }

    /**
     * 对象数据转换
     *
     * @param source           原对象
     * @param destinationClass 目标对象class类
     * @param <T>              返回类型
     * @return 目标对象
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source instanceof BaseModelObject) {
            PropertyFilter profilter = new PropertyFilter() {
                @Override
                public boolean apply(Object object, String name, Object value) {
                    if ("key".equalsIgnoreCase(name)) {
                        //false表示last字段将被排除在外
                        return false;
                    }
                    return true;
                }
            };
            String json = JSON.toJSONString(source, profilter);
            return JSON.parseObject(json, destinationClass);
        }
        return JSON.parseObject(JSON.toJSONString(source), destinationClass);
    }

    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        return JSON.parseArray(JSON.toJSONString(sourceList), destinationClass);
    }
}
