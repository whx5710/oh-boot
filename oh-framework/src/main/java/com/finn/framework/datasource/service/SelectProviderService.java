package com.finn.framework.datasource.service;

import com.finn.core.entity.Parameter;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.ReflectUtil;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.query.Query;
import com.finn.framework.utils.ParameterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 通用provider,拼接增删查改，通过 @SelectProvider注解操作，减少sql编写<br/>
 * 单表查询      selectList selectPage selectPageByParam <br/>
 * 注意：如果对查询性能有要求，不建议使用
 * @author 王小费 whx5710@qq.com
 */
public class SelectProviderService extends ProviderService{

    public static final String SELECT_LIST = "selectList";

    public static final String SELECT_PAGE = "selectPage";

    public static final String SELECT_PARAM_PAGE = "selectPageByParam";

    private static final String queryKey = "query";

    private final Logger log = LoggerFactory.getLogger(SelectProviderService.class);

    /**
     * 单表分页查询
     * @param params query 查询参赛  entityType 实体类
     * @return sql
     */
    public String selectPage(Map<String, Object> params){
        List<Field> entityFields = null;
        String tableName;
        String orderBy = null;
        StringBuilder whereSb = new StringBuilder();
        if(params.containsKey("clazz") && params.get("clazz") instanceof Class<?> clazz){
            tableName = getTableName(clazz);
            entityFields = ReflectUtil.getFields(clazz);
        }else{
            throw new ServerException("未指定实体类，不能进行映射");
        }
        if(params.containsKey(queryKey) && params.get(queryKey) instanceof Query query){
            orderBy = query.getOrderBy();
            // 查询参数属性
            List<Field> queryFields = ReflectUtil.getFields(query.getClass());
            for(Field enField : entityFields){
                for(Field qryField : queryFields){
                    try {
                        if(enField.getName().equals(qryField.getName())  && ReflectUtil.getValue(query, qryField.getName()) != null){
                            if (enField.isAnnotationPresent(TableField.class)) { // 判断是否有该注解
                                TableField annotation = enField.getAnnotation(TableField.class);
                                if (annotation.exists()) { // 剔除非数据库字段
                                    whereSb.append(and).append(annotation.value()).append(" = ").append("#{").append(queryKey).append(".").append(enField.getName()).append("}");
                                }
                            }else{
                                // 无注解的字段默认成与数据库字段一致
                                whereSb.append(and).append(enField.getName()).append(" = ").append("#{").append(queryKey).append(".").append(enField.getName()).append("}");
                            }
                        }
                    } catch (NoSuchFieldException e){
                        log.warn("无{}属性！", qryField.getName());
                    }
                }
            }
        }
        // 拼接
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(tableName);
        // where 条件
        if(!whereSb.isEmpty()){
            sql.append(where).append(whereSb.substring(and.length()));
        }
        // 排序
        if(orderBy != null && !orderBy.isEmpty()){
            orderBy = orderBy.trim();
            if(!orderBy.isEmpty()){
                sql.append(" order by ").append(orderBy);
            }
        }
        log.debug("生成分页查询SQL: {}", sql);
        return sql.toString();
    }

    /**
     * 通用单表查询
     * @param entity 实体类
     * @return sql
     * @param <T> t
     */
    public <T> String selectList(T entity){
        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);
        List<Field> fields = ReflectUtil.getFields(clazz);
        StringBuilder whereSb = new StringBuilder();
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)) { // 判断是否有该注解
                TableField annotation = field.getAnnotation(TableField.class);
                if (annotation.exists()) { // 剔除非数据库字段
                    try {
                        Object object = ReflectUtil.getValue(entity, field.getName());
                        if(object != null){
                            whereSb.append(and).append(annotation.value()).append(" = ").append("#{").append(field.getName()).append("}");
                        }
                    } catch (NoSuchFieldException e){
                        log.warn("无{}属性！", field.getName());
                    }
                }
            } else {
                // 无注解的字段默认成与数据库字段一致
                try {
                    Object object = ReflectUtil.getValue(entity, field.getName());
                    if(object != null){
                        whereSb.append(and).append(field.getName()).append(" = ").append("#{").append(field.getName()).append("}");
                    }
                } catch (NoSuchFieldException e){
                    log.warn("无{}属性！", field.getName());
                }
            }
        }
        // 拼接
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(tableName).append(where).append(whereSb.substring(and.length()));
        log.debug("生成查询SQL: {}", sql);
        return  sql.toString();
    }


    /**
     * 通用单表查询
     * @param param 查询参数
     * @return str
     * @param <T> t
     */
    public <T> String selectPageByParam(ParameterBuilder<T> param){
        Class<T> clazz = param.getClazz();
        String tableName = getTableName(clazz);
        String orderBy = param.getOrderBy();
        StringBuilder whereSb = new StringBuilder();
        if(param.list() != null && !param.list().isEmpty()){
            List<Parameter> list = param.list();
            for(Parameter item: list){
                switch (item.getExpression()) {
                    case ParameterBuilder.EQ ->
                            whereSb.append(and).append(item.getColName()).append(" = #{").append(item.getField()).append("}");
                    case ParameterBuilder.NE ->
                            whereSb.append(and).append(item.getColName()).append(" != #{").append(item.getField()).append("}");
                    case ParameterBuilder.LIKE ->
                            whereSb.append(and).append(item.getColName()).append(" like '%#{").append(item.getField()).append("}%");
                    case ParameterBuilder.IN ->
                            whereSb.append(and).append(item.getColName()).append(" in (#{").append(item.getField()).append("})");
                    default -> System.out.println(item);
                }
            }
        }
        // 拼接
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(tableName);
        // where 条件
        if(!whereSb.isEmpty()){
            sql.append(where).append(whereSb.substring(and.length()));
        }
        // 排序
        if(orderBy != null && !orderBy.isEmpty()){
            orderBy = orderBy.trim();
            if(!orderBy.isEmpty()){
                sql.append(" order by ").append(orderBy);
            }
        }
        log.debug("生成分页SQL: {}", sql);
        return sql.toString();
    }
}
