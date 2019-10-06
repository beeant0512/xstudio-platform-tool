package com.xstudio.tool.request;

import com.alibaba.fastjson.JSON;
import com.xstudio.tool.utils.JavaBeansUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.ws.rs.core.Form;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 对象转NameValuePair对象
 *
 * @author xiaobiao
 * @date #{date}
 */
public class ObjectToNameValuePairUtil {
    private ObjectToNameValuePairUtil() {
    }

    /**
     * 对象转NameValuePair对象
     *
     * @param obj 对象
     * @return {@link Form}
     */
    public static List<NameValuePair> translate(Object obj) {
        List<NameValuePair> list = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        fieldToItem(obj, list, fields, false, false);
        return list;
    }

    /**
     * 对象转NameValuePair对象
     *
     * @param obj            对象
     * @param isListAsString 对象的集合属性是否转成字符串
     * @return {@link Form}
     */
    public static List<NameValuePair> translate(Object obj, Boolean isListAsString) {
        List<NameValuePair> list = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        fieldToItem(obj, list, fields, isListAsString, false);
        return list;
    }

    private static void fieldToItem(Object obj, List<NameValuePair> list, Field[] fields, Boolean isListAsString, Boolean overwrite) {

        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            // list 处理
            if (field.getType().getTypeName().contains("List")) {
                if (isListAsString) {
                    List<?> getter = (List<?>) JavaBeansUtil.getter(obj, field.getName());
                    if (null == getter) {
                        list.add(new BasicNameValuePair(field.getName(), ""));
                    } else {
                        list.add(new BasicNameValuePair(field.getName(), JSON.toJSONString(getter)));
                    }
                } else {
                    doListParam(list, obj, field, overwrite);
                }
            } else {
                setFormParam(list, obj, field.getName(), field, overwrite);
            }
        }
    }

    private static void setFormParam(List<NameValuePair> list, Object obj, String paramName, Field field, boolean overwrite) {
        Class<?> genericType = (Class<?>) field.getGenericType();
        removeExistParam(list, field, overwrite);

        if (String.class.isAssignableFrom(genericType)) {
            list.add(new BasicNameValuePair(paramName, (String) JavaBeansUtil.getter(obj, field.getName())));
        } else if (Number.class.isAssignableFrom(genericType)) {
            if (JavaBeansUtil.getter(obj, field.getName()) != null) {
                list.add(new BasicNameValuePair(paramName, String.valueOf(JavaBeansUtil.getter(obj, field.getName()))));
            }
        } else if (Boolean.class.isAssignableFrom(genericType)) {
            list.add(new BasicNameValuePair(paramName, String.valueOf(Boolean.valueOf((String) JavaBeansUtil.getter(obj, field.getName())))));
        }
    }

    private static void removeExistParam(List<NameValuePair> list, Field field, boolean overwrite) {
        if (overwrite && null != list) {
            Iterator<NameValuePair> iterator = list.iterator();
            NameValuePair next;
            while (iterator.hasNext()) {
                next = iterator.next();
                if (next.getName().equals(field.getName())) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    private static void listToForm(String paramName, Object obj, List<NameValuePair> list, Field[] fields, boolean overwrite) {
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            // list 处理
            if (field.getType().getTypeName().contains("List")) {
                doListParam(list, obj, field, overwrite);
            } else {
                setFormParam(list, obj, paramName + "." + field.getName(), field, overwrite);
            }
        }
    }

    private static void listObjectToForm(String paramName, Object obj, List<NameValuePair> list, boolean overwrite) {
        Field[] fields = obj.getClass().getDeclaredFields();
        listToForm(paramName, obj, list, fields, overwrite);
    }

    private static void doListParam(List<NameValuePair> list, Object obj, Field field, boolean overwrite) {
        List<?> getter = (List<?>) JavaBeansUtil.getter(obj, field.getName());
        if (null == getter) {
            return;
        }
        for (int i = 0; i < getter.size(); i++) {
            Object listObj = getter.get(0);
            Class<?> aClass = listObj.getClass();
            if (aClass.isAssignableFrom(String.class)) {
                removeExistParam(list, field, overwrite);
                list.add(new BasicNameValuePair(field.getName() + "[" + Integer.toString(i) + "]", String.valueOf(getter.get(0))));
            } else {
                listObjectToForm(field.getName() + "[" + Integer.toString(i) + "]", listObj, list, overwrite);
            }
        }
    }
}
