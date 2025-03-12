package com.finn.framework.utils;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import com.finn.core.entity.Parameter;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.ReflectUtil;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.query.Query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数构建类
 * @author 王小费
 * @since 2025-03-12
 */
public class ParameterBuilder<T> extends Query {

    public static final String EQ = "eq"; // 等于
    public static final String NE = "ne"; // 不等于
    public static final String LIKE = "like"; // 模糊查询
    public static final String IN = "in"; // 模糊查询

    List<Parameter> parameters = new ArrayList<>();

    Class<T> clazz;

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> ParameterBuilder<T> of(Class<T> clazz) {
        ParameterBuilder<T> parameterBuilder = new ParameterBuilder<>();
        parameterBuilder.setClazz(clazz);
        return parameterBuilder;
    }

    /**
     * 等于
     * @param function
     * @param value
     * @return
     */
    public ParameterBuilder<T> eq(Func1<T, ?> function, Object value) {
        String fieldName = LambdaUtil.getFieldName(function);
        this.parameters.add(new Parameter(fieldName, EQ, value, getColName(fieldName)));
        return this;
    }

    /**
     * 不等于
     * @param function
     * @param value
     * @return
     */
    public ParameterBuilder<T> ne(Func1<T, ?> function, Object value) {
        String fieldName = LambdaUtil.getFieldName(function);
        this.parameters.add(new Parameter(fieldName, NE, value, getColName(fieldName)));
        return this;
    }

    /**
     * 模糊查询
     * @param function
     * @param value
     * @return
     */
    public ParameterBuilder<T> like(Func1<T, ?> function, Object value) {
        String fieldName = LambdaUtil.getFieldName(function);
        this.parameters.add(new Parameter(fieldName, LIKE, value, getColName(fieldName)));
        return this;
    }

    public List<Parameter> list() {
        return this.parameters;
    }

    /**
     * 获取列名
     * @param fieldName 属性名
     * @return 列名
     */
    private String getColName(String fieldName) {
        Field field = null;
        try {
            field = ReflectUtil.getFieldByClass(clazz, fieldName);
        } catch (NoSuchFieldException e) {
            throw new ServerException("【" + fieldName + "】字段未找到，请检查");
        }
        if(field.isAnnotationPresent(TableField.class)){ // 判断是否有该注解
            TableField annotation = field.getAnnotation(TableField.class);
            if(annotation.exists()){
                return annotation.value();
            }else{
                throw new ServerException("【" + fieldName + "】字段不存在，请检查");
            }
        }else{
            return fieldName;
        }
    }
}
