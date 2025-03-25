package com.finn.framework.utils;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import com.finn.core.exception.ServerException;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;
import java.util.List;

import static com.finn.core.constant.Constant.PAGE_NUM;
import static com.finn.core.constant.Constant.PAGE_SIZE;

/**
 * 参数构建类；支持常用的单表查询语句构建
 * @author 王小费
 * @since 2025-03-12
 */
public class ParamsBuilder<T> extends ParamsSQL<T> {

    // sql构建器
    private SQL sql;
    // 列名
    private HashMap<String, String> colValue;

    // 初始化
    public static <T> ParamsBuilder<T> of(Class<T> clazz) {
        ParamsBuilder<T> params = new ParamsBuilder<>();
        SQL tmpSQL = new SQL();
        // 拼接列名
        HashMap<String, String> colValue = buildColumn(tmpSQL, clazz);
        params.setSql(tmpSQL);
        params.setColValue(colValue); // 列名
        String tableName = getTableName(clazz);
        params.getSql().FROM(tableName); // 表名
        return params;
    }

    public void setSql(SQL sql){
        this.sql = sql;
    }

    public SQL getSql(){
        return sql;
    }

    // 缓存列名
    public void setColValue(HashMap<String, String> colValue){
        this.colValue = colValue;
    }

    /**
     * 根据字段属性名，获取列名
     * @param fieldName 字段名
     * @return 列名
     */
    private String getColName(String fieldName){
        if(colValue.containsKey(fieldName)){
            return colValue.get(fieldName);
        }else{
            throw new ServerException("【" + fieldName + "】字段不存在，请检查");
        }
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
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " = #{" + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " = #{" + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " = #{" + fieldName + "}");
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
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " <> #{" + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " <> #{" + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " <> #{" + fieldName + "}");
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
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " like concat('%',#{" + fieldName + "},'%')");
                    }
                }else{
                    sql.WHERE(colName + " like concat('%',#{" + fieldName + "},'%')");
                }
            }else{
                sql.WHERE(colName + " like concat('%',#{" + fieldName + "},'%')");
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
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " like concat(#{" + fieldName + "},'%')");
                    }
                }else{
                    sql.WHERE(colName + " like concat(#{" + fieldName + "},'%')");
                }
            }else{
                sql.WHERE(colName + " like concat(#{" + fieldName + "},'%')");
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
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " like concat('%',#{" + fieldName + "})");
                    }
                }else{
                    sql.WHERE(colName + " like concat('%',#{" + fieldName + "})");
                }
            }else{
                sql.WHERE(colName + " like concat('%',#{" + fieldName + "})");
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
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " not like concat('%',#{" + fieldName + "},'%')");
                    }
                }else{
                    sql.WHERE(colName + " not like concat('%',#{" + fieldName + "},'%')");
                }
            }else{
                sql.WHERE(colName + " not like concat('%',#{" + fieldName + "},'%')");
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
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " > #{" + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " > #{" + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " > #{" + fieldName + "}");
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
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " >= #{" + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " >= #{" + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " >= #{" + fieldName + "}");
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
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " < #{" + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " < #{" + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " < #{" + fieldName + "}");
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
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " <= #{" + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " <= #{" + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " <= #{" + fieldName + "}");
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
        String colName = getColName(fieldName);
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
        String colName = getColName(fieldName);
        sql.WHERE(colName + " is not null");
        return this;
    }

    /**
     * 拼接in 条件,参数拼接为属性名 fieldName_[0-n]
     * @param function f
     * @param value v
     * @return p
     */
    public ParamsBuilder<T> in(Func1<T, ?> function, List<?> value){
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(colName).append(" in (");
            for(int i = 0; i < value.size(); i++){
                String tmpStr = fieldName + "_" + i;
                if(i == (value.size() -1)){
                    stringBuilder.append("#{").append(tmpStr).append("}");
                }else{
                    stringBuilder.append("#{").append(tmpStr).append("}, ");
                }
                put(tmpStr, value.get(i));
            }
            stringBuilder.append(")");
            sql.WHERE(stringBuilder.toString());
        }
        return this;
    }

    /**
     * 拼接in 条件,参数拼接为属性名 fieldName_[0-n]
     * @param function f
     * @param value v
     * @return p
     */
    public ParamsBuilder<T> in(Func1<T, ?> function, Object... value){
        if(value != null){
            String fieldName = LambdaUtil.getFieldName(function);
            String colName = getColName(fieldName);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(colName).append(" in (");
            for(int i = 0; i < value.length; i++){
                String tmpStr = fieldName + "_" + i;
                if(value[i] != null){
                    if(i == (value.length -1)){
                        stringBuilder.append("#{").append(tmpStr).append("}");
                    }else{
                        stringBuilder.append("#{").append(tmpStr).append("}, ");
                    }
                    put(tmpStr, value[i]);
                }
            }
            stringBuilder.append(")");
            sql.WHERE(stringBuilder.toString());
        }
        return this;
    }

    /**
     * 拼接SQL<br/>
     * 示例：content like concat('%',#{keyWord}, '%') or version_num like concat('%', #{keyWord},'%'))
     * @param whereSQL where条件
     * @param param 参数
     * @return p
     */
    public ParamsBuilder<T> jointSQL(String whereSQL, HashMap<String, Object> param){
        if(param != null && !param.isEmpty()){
            sql.WHERE(whereSQL);
            this.putAll(param);
        }
        return this;
    }

    /**
     * 拼接SQL<br/>
     * 示例：content like concat('%',#{keyWord}, '%') or version_num like concat('%', #{keyWord},'%'))
     * @param whereSQL where条件;user_name = #{userName}
     * @param fieldName userName
     * @param value 参数值
     * @return ParamsBuilder
     */
    public ParamsBuilder<T> jointSQL(String whereSQL, String fieldName, Object value){
        if(value != null && fieldName != null && !fieldName.isEmpty()){
            if(value instanceof String str){
                if(!str.isEmpty()){
                    sql.WHERE(whereSQL);
                    this.put(fieldName, value);
                }
            }else{
                sql.WHERE(whereSQL);
                this.put(fieldName, value);
            }
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
     * @param columns 排序列名；示例：create_time desc
     * @return p
     */
    public ParamsBuilder<T> orderBy(String... columns) {
        if(columns != null && columns.length > 0){
            sql.ORDER_BY(columns);
        }
        return this;
    }

    /**
     * 设置页数
     * @param pageNum i
     * @return p
     */
    public ParamsBuilder<T> pageNum(Integer pageNum) {
        this.put(PAGE_NUM, pageNum);
        return this;
    }

    /**
     * 设置每页数
     * @param pageSize i
     * @return p
     */
    public ParamsBuilder<T> pageSize(Integer pageSize) {
        this.put(PAGE_SIZE, pageSize);
        return this;
    }
}
