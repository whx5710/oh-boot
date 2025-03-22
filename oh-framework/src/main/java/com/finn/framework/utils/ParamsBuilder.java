package com.finn.framework.utils;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

import static com.finn.core.constant.Constant.PAGE_NUM;
import static com.finn.core.constant.Constant.PAGE_SIZE;

/**
 * 参数构建类
 * @author 王小费
 * @since 2025-03-12
 */
public class ParamsBuilder<T> extends ParamsSQL<T> {
    // 类
    private Class<T> clazz;

    private static SQL sql;

    // 初始化
    public static <T> ParamsBuilder<T> of(Class<T> clazz) {
        ParamsBuilder<T> ParamsBuilder = new ParamsBuilder<>();
        String tableName = getTableName(clazz);
        sql = new SQL();
        ParamsBuilder.setClazz(clazz);
        buildColumn(sql, clazz);
        sql.FROM(tableName);
        return ParamsBuilder;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getSqlStr() {
        return sql.toString();
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> eq(Func1<T, ?> function, Object value) {
        return eq(function, value, false);
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public ParamsBuilder<T> eq(Func1<T, ?> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " = #{fp." + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " = #{fp." + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " = #{fp." + fieldName + "}");
            }
            this.put(fieldName, value);
        }
        return this;
    }

    /**
     * 不等于
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> ne(Func1<T, ?> function, Object value) {
        return ne(function, value, false);
    }

    /**
     * 不等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public ParamsBuilder<T> ne(Func1<T, ?> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " <> #{fp." + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " <> #{fp." + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " <> #{fp." + fieldName + "}");
            }
            this.put(fieldName, value);
        }
        return this;
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> like(Func1<T, ?> function, Object value) {
        return like(function, value, false);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public ParamsBuilder<T> like(Func1<T, ?> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " like concat('%',#{fp." + fieldName + "},'%')");
                    }
                }else{
                    sql.WHERE(colName + " like concat('%',#{fp." + fieldName + "},'%')");
                }
            }else{
                sql.WHERE(colName + " like concat('%',#{fp." + fieldName + "},'%')");
            }
            this.put(fieldName, value);
        }
        return this;
    }


    /**
     * 右模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> likeRight(Func1<T, ?> function, Object value) {
        return likeRight(function, value, false);
    }

    /**
     * 右模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public ParamsBuilder<T> likeRight(Func1<T, ?> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " like concat(#{fp." + fieldName + "},'%')");
                    }
                }else{
                    sql.WHERE(colName + " like concat(#{fp." + fieldName + "},'%')");
                }
            }else{
                sql.WHERE(colName + " like concat(#{fp." + fieldName + "},'%')");
            }
            this.put(fieldName, value);
        }
        return this;
    }

    /**
     * 左模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> likeLeft(Func1<T, ?> function, Object value) {
        return likeLeft(function, value, false);
    }

    /**
     * 左模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public ParamsBuilder<T> likeLeft(Func1<T, ?> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " like concat('%',#{fp." + fieldName + "})");
                    }
                }else{
                    sql.WHERE(colName + " like concat('%',#{fp." + fieldName + "})");
                }
            }else{
                sql.WHERE(colName + " like concat('%',#{fp." + fieldName + "})");
            }
            this.put(fieldName, value);
        }
        return this;
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> notLike(Func1<T, ?> function, Object value) {
        return notLike(function, value, false);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public ParamsBuilder<T> notLike(Func1<T, ?> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " not like concat('%',#{fp." + fieldName + "},'%')");
                    }
                }else{
                    sql.WHERE(colName + " not like concat('%',#{fp." + fieldName + "},'%')");
                }
            }else{
                sql.WHERE(colName + " not like concat('%',#{fp." + fieldName + "},'%')");
            }
            this.put(fieldName, value);
        }
        return this;
    }

    /**
     * 大于
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> gt(Func1<T, ?> function, Object value) {
        return gt(function, value, false);
    }

    /**
     * 大于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public ParamsBuilder<T> gt(Func1<T, ?> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " > #{fp." + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " > #{fp." + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " > #{fp." + fieldName + "}");
            }
            this.put(fieldName, value);
        }
        return this;
    }

    /**
     * 大于等于
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> ge(Func1<T, ?> function, Object value) {
        return ge(function, value, false);
    }

    /**
     * 大于等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public ParamsBuilder<T> ge(Func1<T, ?> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " >= #{fp." + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " >= #{fp." + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " >= #{fp." + fieldName + "}");
            }
            this.put(fieldName, value);
        }
        return this;
    }

    /**
     * 小于
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> lt(Func1<T, ?> function, Object value) {
        return lt(function, value, false);
    }

    /**
     * 小于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public ParamsBuilder<T> lt(Func1<T, ?> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " < #{fp." + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " < #{fp." + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " < #{fp." + fieldName + "}");
            }
            this.put(fieldName, value);
        }
        return this;
    }

    /**
     * 小于等于
     * @param function f
     * @param value 值
     * @return p
     */
    public ParamsBuilder<T> le(Func1<T, ?> function, Object value) {
        return le(function, value, false);
    }

    /**
     * 小于等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public ParamsBuilder<T> le(Func1<T, ?> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " <= #{fp." + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " <= #{fp." + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " <= #{fp." + fieldName + "}");
            }
            this.put(fieldName, value);
        }
        return this;
    }


    /**
     * 为空
     * @param function f
     * @return p
     */
    public ParamsBuilder<T> isNull(Func1<T, ?> function) {
        String fieldName = LambdaUtil.getFieldName(function);
        String colName = getColName(fieldName, clazz);
        sql.WHERE(colName + " is null");
        return this;
    }

    /**
     * 不为空
     * @param function f
     * @return p
     */
    public ParamsBuilder<T> isNotNull(Func1<T, ?> function) {
        String fieldName = LambdaUtil.getFieldName(function);
        String colName = getColName(fieldName, clazz);
        sql.WHERE(colName + " is not null");
        return this;
    }

    /**
     * 拼接in 条件
     * @param function f
     * @param value v
     * @return p
     */
    public ParamsBuilder<T> in(Func1<T, ?> function, List<?> value){
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName, clazz);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(colName).append(" in (");
            for(int i = 0; i < value.size(); i++){
                String tmpStr = fieldName + "_" + i;
                if(i == (value.size() -1)){
                    stringBuilder.append("#{fp.").append(tmpStr).append("}");
                }else{
                    stringBuilder.append("#{fp.").append(tmpStr).append("}, ");
                }
                put(tmpStr, value.get(i));
            }
            stringBuilder.append(")");
            sql.WHERE(stringBuilder.toString());
        }
        return this;
    }

    /**
     * 或条件
     * @return p
     */
    public ParamsBuilder<T> or() {
        sql.OR();
        return this;
    }

    /**
     * 排序
     * @param columns 列名
     * @return p
     */
    public ParamsBuilder<T> setOrderBy(String... columns) {
        sql.ORDER_BY(columns);
        return this;
    }

    /**
     * 设置页数
     * @param pageNum i
     * @return p
     */
    public ParamsBuilder<T> setPageNum(Integer pageNum) {
        this.put(PAGE_NUM, pageNum);
        return this;
    }

    /**
     * 设置每页数
     * @param pageSize i
     * @return p
     */
    public ParamsBuilder<T> setPageSize(Integer pageSize) {
        this.put(PAGE_SIZE, pageSize);
        return this;
    }
}
