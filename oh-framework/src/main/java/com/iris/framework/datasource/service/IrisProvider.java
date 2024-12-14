package com.iris.framework.datasource.service;

import com.iris.framework.datasource.annotations.TableColumn;
import com.iris.framework.datasource.annotations.TableName;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通用provider
 */
public class IrisProvider {

    /**
     * 插入数据
     * @param entity
     * @return
     * @param <T>
     */
    public <T> String insertEntity(T entity)  {
        Class<?> clazz = entity.getClass();
        // 获取表名
        TableName apoTable = clazz.getAnnotation(TableName.class);
        String tableName = apoTable.value();
        System.out.println(tableName);
        List<Field> fields = new ArrayList<>();
        while (clazz != null){
            fields.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        String colStr = "";
        String valueStr = "";
        SQL sql = new SQL();
        for(int i = 0; i < fields.size(); i++){
            Field field = fields.get(i);
            if(field.isAnnotationPresent(TableColumn.class)){ // 判断是否有该注解
                TableColumn annotation = field.getAnnotation(TableColumn.class);
                if(!annotation.isExists()){ // 剔除非数据库字段
                    fields.remove(i);
                    i--;
                }else{
                    colStr = String.join(",", colStr, annotation.columnName());
                    valueStr = String.join(",", valueStr, "#{"+field.getName()+"}");
                }
            }else {
                // 无注解的字段默认成与数据库字段一致
                colStr = String.join(",", colStr, field.getName());
                valueStr = String.join(",", valueStr, "#{"+field.getName()+"}");
            }
        }
        colStr = colStr.substring(1);
        valueStr = valueStr.substring(1);
        return "insert into " + tableName + "(" + colStr + ") values (" + valueStr + ")";
    }
}
