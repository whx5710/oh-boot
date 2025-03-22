package com.finn.framework.utils;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.ReflectUtil;
import com.finn.core.utils.Tools;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * SQL构建类
 * @author 王小费
 * @since 2025-03-22
 */
public class ParamsSQL<T> extends HashMap<String, Object> {
    /**
     * 获取表名
     * @param clazz c
     * @return 表名
     */
    static String getTableName(Class<?> clazz){
        // 获取表名
        TableName apoTable = clazz.getAnnotation(TableName.class);
        if(apoTable == null){
            String s = clazz.getName();
            int i = s.lastIndexOf(".");
            return Tools.humpToLine(s.substring(i + 1));
        }else{
            String tableName = apoTable.value();
            if(tableName == null || tableName.isEmpty()){
                throw new ServerException("未指定表名，执行失败");
            }else{
                return tableName;
            }
        }
    }

    /**
     * 组装列名
     *
     * @param sql sql
     */
    static void buildColumn(SQL sql, Class<?> clazz){
        List<Field> fields = ReflectUtil.getFields(clazz);
        for(Field field: fields){
            if (field.isAnnotationPresent(TableField.class)) { // 判断是否有该注解
                TableField annotation = field.getAnnotation(TableField.class);
                if (annotation.exists()) { // 剔除非数据库字段
                    sql.SELECT(annotation.value() + " AS " + field.getName());
                }
            }else{
                sql.SELECT(field.getName());
            }
        }
    }

    /**
     * 获取列名
     * @param fieldName 属性名
     * @return 列名
     */
    String getColName(String fieldName, Class<T> clazz) {
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
