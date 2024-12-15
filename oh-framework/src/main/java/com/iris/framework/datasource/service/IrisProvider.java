package com.iris.framework.datasource.service;

import com.iris.core.exception.ServerException;
import com.iris.framework.datasource.annotations.TableColumn;
import com.iris.framework.datasource.annotations.TableName;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通用provider,拼接增删查改，通过 @InsertProvider、@DeleteProvider 等注解操作，
 * 减少sql编写
 * @author 王小费 whx5710@qq.com
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
        String tableName = clazz.getAnnotation(TableName.class).value();
        if(tableName == null || tableName.isEmpty()){
            throw new ServerException("实体类没指定表名，执行失败！");
        }
        List<Field> fields = getFields(clazz);
        String colStr = "";
        String valueStr = "";
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

    /**
     * 获取实体类属性
     * @param clazz 类
     * @return list
     */
    private List<Field> getFields(Class<?> clazz){
        // 获取表名
        TableName apoTable = clazz.getAnnotation(TableName.class);
        if(apoTable == null){
            throw new ServerException("实体类没指定表名，执行失败！");
        }
        List<Field> fields = new ArrayList<>();
        while (clazz != null){
            fields.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
