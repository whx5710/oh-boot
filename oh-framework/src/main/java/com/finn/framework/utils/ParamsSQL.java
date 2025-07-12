package com.finn.framework.utils;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.ReflectUtil;
import com.finn.core.utils.Tools;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableId;
import com.finn.framework.datasource.annotations.TableName;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * SQL构建类
 * @author 王小费
 * @since 2025-03-22
 */
public class ParamsSQL<T> extends HashMap<String, Object> {

    private final static Logger log = LoggerFactory.getLogger(ParamsSQL.class);

    // 列名
    protected HashMap<String, String> colValue;

    // 缓存列名
    public void setColValue(HashMap<String, String> colValue){
        this.colValue = colValue;
    }

    /**
     * 根据字段属性名，获取列名
     * @param fieldName 字段名
     * @return 列名
     */
    protected String getColName(String fieldName){
        if(colValue.containsKey(fieldName)){
            return colValue.get(fieldName);
        }else{
            throw new ServerException("【" + fieldName + "】字段不存在，请检查");
        }
    }

    /**
     * 获取表名
     * @param clazz c
     * @return 表名
     */
    public static String getTableName(Class<?> clazz){
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
     * 组装列名，缓存列名
     *
     * @param sql sql
     * @return colValue
     */
    static HashMap<String, String> buildColumn(SQL sql, Class<?> clazz){
        HashMap<String, String> colValue = new HashMap<>();
        List<Field> fields = ReflectUtil.getFields(clazz);
        HashMap<String, Boolean> judge = new HashMap<>();
        for(Field field: fields){
            if (field.isAnnotationPresent(TableField.class)) { // 判断是否有该注解
                TableField annotation = field.getAnnotation(TableField.class);
                if (annotation.exists()) { // 剔除非数据库字段
                    if((judge.containsKey(field.getName()) && !judge.get(field.getName())) ||
                            (judge.containsKey(annotation.value()) && !judge.get(annotation.value()))){
                        log.warn("子类有覆盖 {} 字段，不查询该字段", annotation.value());
                    }else{
                        sql.SELECT(annotation.value() + " AS " + field.getName());
                        colValue.put(field.getName(), annotation.value()); // 缓存列名
                    }
                }else{
                    // 如果子类覆盖了父类的属性，存在 exists = false的情况
                    String key = annotation.value()==null?field.getName():annotation.value();
                    if(key.equals("")){
                        key = field.getName();
                    }
                    judge.put(key, annotation.exists());
                }
            }else{
                sql.SELECT(field.getName());
                colValue.put(field.getName(), field.getName()); // 缓存列名
            }
        }
        return colValue;
    }

    /**
     * 获取主键
     * @param clazz class
     * @return str
     */
    public static String getPriKey(Class<?> clazz){
        List<Field> fields = ReflectUtil.getFields(clazz);
        for(Field field: fields){
            if (field.isAnnotationPresent(TableId.class)) { // 判断是否有该注解
                TableId annotation = field.getAnnotation(TableId.class);
                return annotation.value();
            }
        }
        return "id";
    }

}
