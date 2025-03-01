package com.finn.core.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 反射工具类
 *
 * @author 王小费 whx5710@qq.com
 * @since 2025-03-01
 */
public class ReflectUtil {

    /**
     * 获取属性值，无对应属性需捕获异常
     * @param object 对象
     * @param fieldName 属性名
     * @return 值
     * @throws NoSuchFieldException 异常
     */
    public static Object getValue(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        //如果有，设置Accessible为true,启用安全检查（为true时可以使用反射访问私有变量，否则不能访问私有变量）
        field.setAccessible(true);
        return ReflectionUtils.getField(field, object);
    }

    /**
     * 设置属性值，无对应属性需捕获异常
     * @param object 对象
     * @param fieldName 属性名
     * @param value 值
     * @throws NoSuchFieldException 异常
     */
    public static void setValue(Object object,String fieldName, Object value) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        ReflectionUtils.setField(field, object, value);
    }
}
