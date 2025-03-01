package com.finn.framework.datasource.service;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.ReflectUtil;
import com.finn.core.utils.Tools;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableId;
import com.finn.framework.datasource.annotations.TableName;
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
public class ProviderService {

    public static final String INSERT = "insert";
    public static final String UPDATE = "updateById";
    public static final String DELETE = "delete";

    private final Logger log = LoggerFactory.getLogger(ProviderService.class);

    String comma = ", ";
    String where = " where ";
    String and = " and ";

    /**
     * 插入数据
     * @param entity
     * @return
     * @param <T>
     */
    public <T> String insert(T entity) {
        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);
        List<Field> fields = ReflectUtil.getFields(clazz);
        StringBuilder sbCol = new StringBuilder();
        StringBuilder sbValue = new StringBuilder();
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)) { // 判断是否有该注解
                TableField annotation = field.getAnnotation(TableField.class);
                if (annotation.exists()) { // 剔除非数据库字段
                    sbCol.append(comma).append(annotation.value());
                    sbValue.append(comma).append("#{").append(field.getName()).append("}");
                }
            } else {
                // 无注解的字段默认成与数据库字段一致
                sbCol.append(comma).append(Tools.humpToLine(field.getName()));
                sbValue.append(comma).append("#{").append(field.getName()).append("}");
            }
        }
        // 拼接
        String colStr = sbCol.substring(comma.length());
        String valueStr = sbValue.substring(comma.length());
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(tableName).append("(").append(colStr).append(") values (").append(valueStr).append(")");
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
        String tableName = getTableName(clazz); // 表名
        StringBuilder sb = new StringBuilder();
        boolean hasId = false;
        boolean hasMultipleId = false;
        StringBuilder sbWhere = new StringBuilder();
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
                            sb.append(comma).append(annotation.value()).append(" = #{").append(field.getName()).append("}");
                        }
                    }catch (NoSuchFieldException e){
                        log.warn("无{}属性！", field.getName());
                    }
                }
            }else if(field.isAnnotationPresent(TableId.class)){
                TableId tableId = field.getAnnotation(TableId.class);
                if(tableId.value() != null && !tableId.value().isEmpty()){
                    sbWhere.append(and).append(tableId.value()).append(" = #{").append(field.getName()).append("}");
                }else{
                    sbWhere.append(and).append(field.getName()).append(" = #{").append(field.getName()).append("}");
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
                        sb.append(comma).append(Tools.humpToLine(field.getName())).append(" = #{").append(field.getName()).append("}");
                    }
                } catch (NoSuchFieldException e){
                    log.warn("无{}属性！", field.getName());
                }
            }
        }
        String sqlWhere = where;
        if(hasMultipleId){
            sqlWhere = sqlWhere + sbWhere.substring(and.length());
        }else if(hasId){
            sqlWhere = sqlWhere + "id = #{id}";
        }else{
            throw new ServerException("无id字段或指定条件ID字段，不能进行更新！");
        }
        String setStr = sb.substring(comma.length());
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tableName).append(" set ").append(setStr).append(sqlWhere);
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
        String tableName = getTableName(clazz); // 表名
        List<Field> fields = ReflectUtil.getFields(clazz); // 属性列表
        StringBuilder sbWhere = new StringBuilder();
        boolean hasId = false;
        boolean hasMultipleId = false;
        for(Field field : fields){
            if(field.isAnnotationPresent(TableId.class)){
                TableId tableId = field.getAnnotation(TableId.class);
                if(tableId.value() != null && !tableId.value().isEmpty()){
                    sbWhere.append(and).append(tableId.value()).append(" = #{").append(field.getName()).append("}");
                }else{
                    sbWhere.append(and).append(Tools.humpToLine(field.getName())).append(" = #{").append(field.getName()).append("}");
                }
                hasMultipleId = true;
            }else{
                // 无注解的字段默认成与数据库字段一致
                if (field.getName().equals("id")) {
                    hasId = true;
                }
            }
        }
        String sqlWhere = where;
        if(hasMultipleId){
            sqlWhere = sqlWhere + sbWhere.substring(and.length());
        }else if(hasId){
            sqlWhere = sqlWhere + "id = #{id}";
        }else{
            throw new ServerException("无id字段或指定条件ID字段，不能进行删除！");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(tableName).append(sqlWhere);
        log.debug("生成删除SQL: {}", sql);
        return sql.toString();
    }

    /**
     * 获取表名
     * @param clazz c
     * @return 表名
     */
    private String getTableName(Class<?> clazz){
        // 获取表名
        TableName apoTable = clazz.getAnnotation(TableName.class);
        if(apoTable == null){
            log.warn("实体类没指定表名（@TableName），默认使用类名作为表名");
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

}
