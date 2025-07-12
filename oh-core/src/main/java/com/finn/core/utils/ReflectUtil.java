package com.finn.core.utils;

import com.finn.core.aspect.FuncUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射工具类
 *
 * @author 王小费 whx5710@qq.com
 * @since 2025-03-01
 */
public class ReflectUtil {
    private static final Map<FuncUtils<?>, Field> FUNCTION_CACHE = new ConcurrentHashMap<>();

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
     * 获取 Field
     * @param clazz 对象
     * @param fieldName 属性名
     * @return Field
     * @throws NoSuchFieldException 异常
     */
    public static Field getFieldByClass(Class<?> clazz, String fieldName) throws NoSuchFieldException {
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
        return clazz.getDeclaredField(fieldName);
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

    /**
     * 根据函数式接口获取Field名称
     * @param function 函数
     * @return Field名称
     * @param <T>
     */
    public static <T> String getFieldName(FuncUtils<T> function) {
        Field field = ReflectUtil.getField(function);
        return field.getName();
    }

    /**
     * 根据函数式接口获取Field
     * @param function
     * @return
     * @param <T>
     */
    public static <T> Field getField(FuncUtils<T> function) {
        return FUNCTION_CACHE.computeIfAbsent(function, ReflectUtil::findField);
    }

    public static <T> Field findField(FuncUtils<T> function) {
        // 第1步 获取SerializedLambda
        final SerializedLambda serializedLambda = getSerializedLambda(function);
        // 第2步 implMethodName 即为Field对应的Getter方法名
        final String implClass = serializedLambda.getImplClass();
        final String implMethodName = serializedLambda.getImplMethodName();
        final String fieldName = convertToFieldName(implMethodName);
        // 第3步  Spring 中的反射工具类获取Class中定义的Field
        final Field field = getField(fieldName, serializedLambda);

        // 第4步 如果没有找到对应的字段应该抛出异常
        if (field == null) {
            throw new RuntimeException("No such class 「"+ implClass +"」 field 「" + fieldName + "」.");
        }
        return field;
    }

    static <T> SerializedLambda getSerializedLambda(FuncUtils<T> function) {
        try {
            Method method = function.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            return (SerializedLambda) method.invoke(function);
        }
        catch (Exception e) {
            throw new RuntimeException("get SerializedLambda exception.", e);
        }
    }

    static String convertToFieldName(String getterMethodName) {
        // 获取方法名
        String prefix = null;
        if (getterMethodName.startsWith("get")) {
            prefix = "get";
        }
        else if (getterMethodName.startsWith("is")) {
            prefix = "is";
        }

        if (prefix == null) {
            throw new IllegalArgumentException("invalid getter method: " + getterMethodName);
        }

        // 截取get/is之后的字符串并转换首字母为小写
        return Introspector.decapitalize(getterMethodName.replace(prefix, ""));
    }

    static Field getField(String fieldName, SerializedLambda serializedLambda) {
        try {
            // 获取的Class是字符串，并且包名是“/”分割，需要替换成“.”，才能获取到对应的Class对象
            String declaredClass = serializedLambda.getImplClass().replace("/", ".");
            Class<?>aClass = Class.forName(declaredClass, false, ClassUtils.getDefaultClassLoader());
            return ReflectionUtils.findField(aClass, fieldName);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("get class field exception.", e);
        }
    }
}
