package com.xstudio.tool.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JavaBeansUtil {
    private static final Logger logger = LogManager.getLogger(JavaBeansUtil.class);

    private JavaBeansUtil() {
        super();
    }

    /**
     * 取Bean的属性和值对应关系的MAP
     *
     * @param object
     * @return Map
     */
    public static Map<String, Object> getFieldValueMap(Object object) {
        Class<?> cls = object.getClass();
        Map<String, Object> valueMap = new HashMap<String, Object>();
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            try {
                String fieldType = field.getType().getSimpleName();
                String fieldGetName = getGetterMethodName(field.getName());
                if (!checkGetMet(methods, fieldGetName)) {
                    continue;
                }
                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[]{});
                Object fieldVal = fieldGetMet.invoke(object, new Object[]{});
                if ("Date".equals(fieldType)) {
                    valueMap.put(field.getName(), DateUtil.format((Date) fieldVal));
                } else {
                    if (null != fieldVal) {
                        valueMap.put(field.getName(), fieldVal);
                    }
                }
            } catch (Exception e) {
                logger.warn("获取字段失败 {}", e);
                continue;
            }
        }
        return valueMap;
    }

    /**
     * 判断是否存在某属性的 get方法
     *
     * @param methods
     * @param fieldGetMet
     * @return boolean
     */
    public static boolean checkGetMet(Method[] methods, String fieldGetMet) {
        for (Method met : methods) {
            if (fieldGetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param obj       操作的对象
     * @param fieldName 操作的属性值
     * @param value     设置的值
     */
    public static void setter(Object obj, String fieldName, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = getSetterMethodName(fieldName);
        Method setMethod = obj.getClass().getMethod(methodName, value.getClass());
        if (null != setMethod) {
            setMethod.invoke(obj, value);
        }
    }

    /**
     * getter方法
     *
     * @param obj       对象
     * @param fieldName 字段
     */
    public static Object getter(Object obj, String fieldName) {
        String methodName = getGetterMethodName(fieldName);
        try {
            Method getMethod = obj.getClass().getMethod(methodName);
            return getMethod.invoke(obj);
        } catch (Exception e) {
            try {
                return obj.getClass().getDeclaredField(fieldName).get(obj);
            } catch (Exception e1) {
                logger.error("获取{} get方法失败", fieldName, e);
            }
        }
        return null;
    }

    public static String getGetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();

        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }

        sb.insert(0, "get");

        return sb.toString();
    }


    public static String getSetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();

        sb.append(property);
        boolean upperFirstCharacter = Character.isLowerCase(sb.charAt(0)) && (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1)));
        if (upperFirstCharacter) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        sb.insert(0, "set");

        return sb.toString();
    }

    /**
     * Gets the camel case string.
     *
     * @param inputString             the input string
     * @param firstCharacterUppercase the first character uppercase
     * @return the camel case string
     */
    public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

}
