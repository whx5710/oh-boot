package com.finn.framework.utils;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import com.finn.core.entity.Parameter;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.ReflectUtil;
import com.finn.core.utils.Tools;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.query.Query;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 参数构建类
 * @author 王小费
 * @since 2025-03-12
 */
public class ParamsBuilder<T> extends Query {

    private final Logger log = LoggerFactory.getLogger(ParamsBuilder.class);

    public static final String EQ = "eq"; // 等于
    public static final String NE = "ne"; // 不等于
    public static final String LIKE = "like"; // 模糊查询
    public static final String IN = "in"; // 模糊查询

    // 参数集合
    List<Parameter> parameters = new ArrayList<>();
    // 查询参数
    Map<String,Object> selectParams = new HashMap<>();

    Class<T> clazz;

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> ParamsBuilder<T> of(Class<T> clazz) {
        ParamsBuilder<T> paramsBuilder = new ParamsBuilder<>();
        paramsBuilder.setClazz(clazz);
        return paramsBuilder;
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> eq(Func1<T, ?> function, Object value) {
        String fieldName = LambdaUtil.getFieldName(function);
        this.parameters.add(new Parameter(fieldName, EQ, value, getColName(fieldName)));
        return this;
    }

    /**
     * 不等于
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> ne(Func1<T, ?> function, Object value) {
        String fieldName = LambdaUtil.getFieldName(function);
        this.parameters.add(new Parameter(fieldName, NE, value, getColName(fieldName)));
        return this;
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> like(Func1<T, ?> function, Object value) {
        String fieldName = LambdaUtil.getFieldName(function);
        this.parameters.add(new Parameter(fieldName, LIKE, value, getColName(fieldName)));
        return this;
    }

    /**
     * 在某范围
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> in(Func1<T, ?> function, Object value) {
        String fieldName = LambdaUtil.getFieldName(function);
        this.parameters.add(new Parameter(fieldName, IN, value, getColName(fieldName)));
        return this;
    }

    public List<Parameter> list() {
        return this.parameters;
    }

    public Map<String, Object> getSelectParams(){
        return this.selectParams;
    }

    /**
     * 拼接查询sql
     * @return sql
     */
    public String buildSelectSQL(){
        AssertUtils.isNull(clazz, "实体对象类");
        String tableName = getTableName(clazz);
        SQL sql = new SQL();
        sql.SELECT("*").FROM(tableName);
        if(!parameters.isEmpty()){
            for(Parameter item: parameters){
                selectParams.put(item.getField(), item.getValue());
                switch (item.getExpression()) {
                    case EQ -> sql.WHERE(item.getColName() + " = #{fp." + item.getField() + "}");
                    case NE -> sql.WHERE(item.getColName() + " != #{fp." + item.getField() + "}");
                    case LIKE -> sql.WHERE(item.getColName() + " like concat('%',#{fp." + item.getField() + "},'%')");
                    case IN -> sql.WHERE(item.getColName() + " in " + buildInStr(item));
                    default -> log.warn("未知的表达式！{}", item.getExpression());
                }
            }
        }
        log.debug("生成查询SQL: {}", sql);
        return sql.toString();
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

    /**
     * 拼接in 条件
     * @param param p
     * @return sql
     */
    private StringBuilder buildInStr(Parameter param){
        StringBuilder stringBuilder = new StringBuilder();
        if(param.getValue() instanceof Collection<?> collection){
            return stringBuilder.append("<foreach item=\"iValue\" index=\"index\" collection=\"")
                    .append("#{fp.").append(param.getField()).append("}")
                    .append("\" open=\"(\" separator=\",\" close=\")\">")
                    .append("#{iValue}").append("</foreach>");
        }else{
            throw new ServerException("查询条件异常，请联系管理员[非集合参数]");
        }
    }
}
