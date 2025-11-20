package com.finn.framework.datasource.service;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.ReflectUtil;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableId;
import com.finn.framework.datasource.utils.InsertWrapper;
import com.finn.framework.datasource.utils.Wrapper;
import com.finn.framework.datasource.utils.DeleteWrapper;
import com.finn.framework.datasource.utils.UpdateWrapper;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
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
    public static final String INSERT_PARAM = "insertByWrapper";
    public static final String UPDATE = "updateById";
    public static final String DELETE = "delete";
    public static final String DELETE_PARAM = "deleteByWrapper";
    public static final String UPDATE_PARAM = "updateByWrapper";

    private final Logger log = LoggerFactory.getLogger(ModifyProviderService.class);

    /**
     * 插入数据
     * @param entity e
     * @return s
     * @param <T> t
     */
    public <T> String insert(T entity) {
        HashMap<String, Boolean> judge = new HashMap<>(); // 判断字段是否存在
        SQL sql = new SQL();
        Class<?> clazz = entity.getClass();
        String tableName = Wrapper.getTableName(clazz);
        List<Field> fields = ReflectUtil.getFields(clazz);
        sql.INSERT_INTO(tableName);
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)) { // 判断是否有该注解
                TableField annotation = field.getAnnotation(TableField.class);
                if (annotation.exists()) { // 剔除非数据库字段
                    if((judge.containsKey(field.getName()) && !judge.get(field.getName())) ||
                            (judge.containsKey(annotation.value()) && !judge.get(annotation.value()))){
                        log.warn("子类有覆盖 {} 字段，不新增该字段", annotation.value());
                    }else{
                        sql.VALUES(annotation.value(), "#{" + field.getName() + "}");
                    }
                }else{
                    // 如果子类覆盖了父类的属性，存在 exists = false的情况
                    String key = annotation.value()==null?field.getName():annotation.value();
                    if(key.isEmpty()){
                        key = field.getName();
                    }
                    judge.put(key, false);
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
        HashMap<String, Boolean> judge = new HashMap<>(); // 判断字段是否存在
        Class<?> clazz = entity.getClass();
        List<Field> fields = ReflectUtil.getFields(clazz); // 属性列表
        String tableName = Wrapper.getTableName(clazz); // 表名
        boolean hasId = false;
        boolean hasMultipleId = false;
        SQL sql = new SQL();
        sql.UPDATE(tableName);
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)) { // 判断是否有该注解
                TableField annotation = field.getAnnotation(TableField.class);
                if (annotation.exists()) { // 剔除非数据库字段
                    if((judge.containsKey(field.getName()) && !judge.get(field.getName())) ||
                            (judge.containsKey(annotation.value()) && !judge.get(annotation.value()))){
                        log.warn("子类有覆盖 {} 字段，不更新该字段", annotation.value());
                    }else{
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
                }else{
                    // 如果子类覆盖了父类的属性，存在 exists = false的情况
                    String key = annotation.value()==null?field.getName():annotation.value();
                    if(key.isEmpty()){
                        key = field.getName();
                    }
                    judge.put(key, false);
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
        String tableName = Wrapper.getTableName(clazz); // 表名
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

    /**
     * 删除
     * @param fp 删除条件构造器
     * @return sql
     * @param <T> t
     */
    public <T> String deleteByWrapper(DeleteWrapper<T> fp){
        return fp.getSql().toString();
    }

    /**
     * 修改sql
     * @param fp 参数 + sql
     * @return sql
     * @param <T> c
     */
    public <T> String updateByWrapper(UpdateWrapper<T> fp){
        return fp.getSql().toString();
    }

    /**
     * 新增sql
     * @param fp 参数 + sql
     * @return sql
     */
    public String insertByWrapper(InsertWrapper fp){
        return fp.getSql().toString();
    }

}
