package com.finn.core.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Field field = getField(object, fieldName);
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
        setValue(object, fieldName, value, true);
    }

    /**
     * 设置属性值，无对应属性需捕获异常
     * @param object 对象
     * @param fieldName 属性名
     * @param value 值
     * @param cover 是否强制覆盖原来的值，false时，只覆盖为null的属性值
     * @throws NoSuchFieldException 异常
     */
    public static void setValue(Object object,String fieldName, Object value, Boolean cover) throws NoSuchFieldException {
        Field field = getField(object, fieldName);
        //如果有，设置Accessible为true,启用安全检查（为true时可以使用反射访问私有变量，否则不能访问私有变量）
        field.setAccessible(true);
        if(cover || ReflectionUtils.getField(field, object) == null){
            ReflectionUtils.setField(field, object, value);
        }
    }

    /**
     * 获取 Field
     * @param object 对象
     * @param fieldName 属性名
     * @return Field
     * @throws NoSuchFieldException 异常
     */
    public static Field getField(Object object, String fieldName) throws NoSuchFieldException {
        Class<?> clazz = object.getClass();
        List<Field> list = getFields(clazz);
        if(!list.isEmpty()){
            for(Field field: list){
                if(field.getName().equals(fieldName)){
                    return field;
                }
            }
        }else{
            throw new NoSuchFieldException();
        }
        return object.getClass().getDeclaredField(fieldName);
    }

    /**
     * 获取所有 Field
     * @param clazz 类
     * @return list
     */
    public static List<Field> getFields(Class<?> clazz){
        List<Field> fields = new ArrayList<>();
        while (clazz != null){
            fields.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            // 父级类中的属性
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

}
