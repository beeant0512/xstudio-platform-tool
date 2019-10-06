package com.xstudio.tool.request;

import com.alibaba.fastjson.JSON;
import com.xstudio.tool.utils.JavaBeansUtil;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author xiaobiao
 * @date #{date}
 */
public class ObjectToFormUtil {
    private ObjectToFormUtil() {
    }

    /**
     * 对象转Form对象
     *
     * @param obj 对象
     * @return {@link Form}
     */
    public static Form translate(Object obj) {
        Form form = new Form();
        Field[] fields = obj.getClass().getDeclaredFields();
        fieldToForm(obj, form, fields, false, false);
        return form;
    }

    /**
     * 对象转Form对象
     *
     * @param obj            对象
     * @param isListAsString 对象的集合属性是否转成字符串
     * @return {@link Form}
     */
    public static Form translate(Object obj, Boolean isListAsString) {
        Form form = new Form();
        Field[] fields = obj.getClass().getDeclaredFields();
        fieldToForm(obj, form, fields, isListAsString, false);
        return form;
    }

    /**
     * 追加参数
     *
     * @param form Form对象
     * @param obj  追加的对象
     */
    public static void appendParam(Form form, Object obj) {
        Boolean isListAsString = false;
        Field[] fields = obj.getClass().getDeclaredFields();
        fieldToForm(obj, form, fields, isListAsString, true);
    }

    /**
     * @param form      Form对象
     * @param obj       追加的对象
     * @param overwrite 是否覆盖
     */
    public static void appendParam(Form form, Object obj, boolean overwrite) {
        Boolean isListAsString = false;
        Field[] fields = obj.getClass().getDeclaredFields();
        fieldToForm(obj, form, fields, isListAsString, overwrite);
    }

    private static void fieldToForm(Object obj, Form form, Field[] fields, Boolean isListAsString, Boolean overwrite) {

        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            // list 处理
            if (field.getType().getTypeName().contains("List")) {
                if (isListAsString) {
                    List<?> getter = (List<?>) JavaBeansUtil.getter(obj, field.getName());
                    if (null == getter) {
                        form.param(field.getName(), "");
                    } else {
                        form.param(field.getName(), JSON.toJSONString(getter));
                    }
                } else {
                    doListParam(form, obj, field, overwrite);
                }
            } else {
                setFormParam(form, obj, field.getName(), field, overwrite);
            }
        }
    }

    private static void setFormParam(Form form, Object obj, String paramName, Field field, boolean overwrite) {
        Class<?> genericType = (Class<?>) field.getGenericType();
        removeExistParam(form, field, overwrite);

        if (String.class.isAssignableFrom(genericType)) {
            form.param(paramName, (String) JavaBeansUtil.getter(obj, field.getName()));
        } else if (Number.class.isAssignableFrom(genericType)) {
            if (JavaBeansUtil.getter(obj, field.getName()) != null) {
                form.param(paramName, String.valueOf(JavaBeansUtil.getter(obj, field.getName())));
            }
        } else if (Boolean.class.isAssignableFrom(genericType)) {
            form.param(paramName, String.valueOf(Boolean.valueOf((String) JavaBeansUtil.getter(obj, field.getName()))));
        }
    }

    private static void removeExistParam(Form form, Field field, boolean overwrite) {
        MultivaluedMap<String, String> stringStringMultivaluedMap = form.asMap();
        List<String> list = stringStringMultivaluedMap.get(field.getName());
        if (overwrite && null != list) {
            stringStringMultivaluedMap.remove(field.getName());
            form = new Form(stringStringMultivaluedMap);
        }
    }

    private static void listToForm(String paramName, Object obj, Form form, Field[] fields, boolean overwrite) {
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            // list 处理
            if (field.getType().getTypeName().contains("List")) {
                doListParam(form, obj, field, overwrite);
            } else {
                setFormParam(form, obj, paramName + "." + field.getName(), field, overwrite);
            }
        }
    }

    private static void listObjectToForm(String paramName, Object obj, Form form, boolean overwrite) {
        Field[] fields = obj.getClass().getDeclaredFields();
        listToForm(paramName, obj, form, fields, overwrite);
    }

    private static void doListParam(Form form, Object obj, Field field, boolean overwrite) {
        List<?> getter = (List<?>) JavaBeansUtil.getter(obj, field.getName());
        if (null == getter) {
            return;
        }
        for (int i = 0; i < getter.size(); i++) {
            Object listObj = getter.get(0);
            Class<?> aClass = listObj.getClass();
            if (aClass.isAssignableFrom(String.class)) {
                removeExistParam(form, field, overwrite);
                form.param(field.getName() + "[" + Integer.toString(i) + "]", String.valueOf(getter.get(0)));
            } else {
                listObjectToForm(field.getName() + "[" + Integer.toString(i) + "]", listObj, form, overwrite);
            }

        }
    }
}
