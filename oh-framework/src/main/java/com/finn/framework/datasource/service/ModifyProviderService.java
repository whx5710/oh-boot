package com.finn.framework.datasource.service;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.ReflectUtil;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableId;
import com.finn.framework.utils.ParamsSQL;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 通用provider,拼接增删查改，通过 @InsertProvider、@UpdateProvider等注解操作，减少sql编写<br/>
 * 单表插入      insert <br/>
 * 根据ID更新数据 updateById，更新时，主键不一定是id，可以通过@TableId 指定<br/>
 * 根据ID删除数据 deleteById
 * @author 王小费 whx5710@qq.com
 */
public class ModifyProviderService {

    public static final String INSERT = "insert";
    public static final String UPDATE = "updateById";
    public static final String DELETE = "delete";

    private final Logger log = LoggerFactory.getLogger(ModifyProviderService.class);

    /**
     * 插入数据
     * @param entity e
     * @return s
     * @param <T> t
     */
    public <T> String insert(T entity) {
        SQL sql = new SQL();
        Class<?> clazz = entity.getClass();
        String tableName = ParamsSQL.getTableName(clazz);
        List<Field> fields = ReflectUtil.getFields(clazz);
        sql.INSERT_INTO(tableName);
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)) { // 判断是否有该注解
                TableField annotation = field.getAnnotation(TableField.class);
                if (annotation.exists()) { // 剔除非数据库字段
                    sql.VALUES(annotation.value(), "#{" + field.getName() + "}");
                }
            } else {
                // 无注解的字段默认成与数据库字段一致
                sql.VALUES(field.getName(), "#{" + field.getName() + "}");
            }
        }
        log.debug("生成插入SQL: {}", sql);
        return  sql.toString();
    }

    /**
     * 根据ID更新数据
     * @param entity 实体
     * @return sql
     * @param <T> p
     */
    public <T> String updateById(T entity) {
        Class<?> clazz = entity.getClass();
        List<Field> fields = ReflectUtil.getFields(clazz); // 属性列表
        String tableName = ParamsSQL.getTableName(clazz); // 表名
        boolean hasId = false;
        boolean hasMultipleId = false;
        SQL sql = new SQL();
        sql.UPDATE(tableName);
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)) { // 判断是否有该注解
                TableField annotation = field.getAnnotation(TableField.class);
                if (annotation.exists()) { // 剔除非数据库字段
                    if (annotation.value().equals("id")) {
                        hasId = true;
                    }
                    // 不为空才更新
                    try {
                        Object object = ReflectUtil.getValue(entity, field.getName());
                        if(object != null){
                            sql.SET(annotation.value() + " = #{" + field.getName() + "}");
                        }
                    }catch (NoSuchFieldException e){
                        log.warn("无{}属性！", field.getName());
                    }
                }
            }else if(field.isAnnotationPresent(TableId.class)){
                TableId tableId = field.getAnnotation(TableId.class);
                if(tableId.value() != null && !tableId.value().isEmpty()){
                    sql.WHERE(tableId.value() + " = #{" + field.getName() + "}");
                }else{
                    sql.WHERE(field.getName() + " = #{" + field.getName() + "}");
                }
                hasMultipleId = true;
            } else {
                // 无注解的字段默认成与数据库字段一致
                if (field.getName().equals("id")) {
                    hasId = true;
                }
                try {
                    Object object = ReflectUtil.getValue(entity, field.getName());
                    if(object != null){
                        sql.SET(field.getName() + " = #{" + field.getName() + "} ");
                    }
                } catch (NoSuchFieldException e){
                    log.warn("无{}属性！", field.getName());
                }
            }
        }
        if(!hasMultipleId){
            if(hasId){
                sql.WHERE("id = #{id}");
            }else {
                throw new ServerException("无id字段或指定条件ID字段，不能进行更新！");
            }
        }
        log.debug("生成修改SQL: {}", sql);
        return  sql.toString();
    }

    /**
     * 删除数据
     * @param entity 实体
     * @return sql
     * @param <T> t
     */
    public <T> String delete(T entity) {
        Class<?> clazz = entity.getClass();
        String tableName = ParamsSQL.getTableName(clazz); // 表名
        List<Field> fields = ReflectUtil.getFields(clazz); // 属性列表
        boolean hasId = false;
        boolean hasMultipleId = false;
        SQL sql = new SQL();
        sql.DELETE_FROM(tableName);
        for(Field field : fields){
            if(field.isAnnotationPresent(TableId.class)){
                TableId tableId = field.getAnnotation(TableId.class);
                if(tableId.value() != null && !tableId.value().isEmpty()){
                    sql.WHERE(tableId.value() + " = #{" + field.getName() + "}");
                }else{
                    sql.WHERE(field.getName() + " = #{" + field.getName() + "}");
                }
                hasMultipleId = true;
            }else{
                // 无注解的字段默认成与数据库字段一致
                if (field.getName().equals("id")) {
                    hasId = true;
                }
            }
        }
        if(!hasMultipleId){
            if(hasId){
                sql.WHERE("id = #{id}");
            }else {
                throw new ServerException("无id字段或指定条件ID字段，不能进行删除！");
            }
        }
        log.debug("生成删除SQL: {}", sql);
        return sql.toString();
    }
}
