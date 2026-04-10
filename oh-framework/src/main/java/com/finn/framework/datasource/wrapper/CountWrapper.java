package com.finn.framework.datasource.wrapper;

import com.finn.framework.aop.annotations.FuncUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * SQL 统计语句构建器<br/>
 * 参数构建类；支持常用的单表查询语句构建
 * @author 王小费
 * @since 2026-02-03
 */
public class CountWrapper<T> extends Wrapper<T> {

    /**
     * 初始化,构建SQL：select count(1) from tableName
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> CountWrapper<T> of(Class<T> clazz) {
        CountWrapper<T> params = new CountWrapper<>();
        SQL tmpSQL = new SQL();
        tmpSQL.SELECT("COUNT(*) AS _COUNT");
        params.setSql(tmpSQL);
        params.setColValue(buildColumn(clazz)); // 列-值
        String tableName = getTableName(clazz);
        params.getSql().FROM(tableName); // 表名
        return params;
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public CountWrapper<T> eq(FuncUtils<T> function, Object value) {
        return (CountWrapper<T>) super.eq(function, value, false);
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public CountWrapper<T> eq(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (CountWrapper<T>) super.eq(function, value, isEmpty);
    }

    /**
     * 不等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public CountWrapper<T> ne(FuncUtils<T> function, Object value) {
        return (CountWrapper<T>) super.ne(function, value, false);
    }

    /**
     * 不等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public CountWrapper<T> ne(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (CountWrapper<T>) super.ne(function, value, isEmpty);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public CountWrapper<T> like(FuncUtils<T> function, Object value) {
        return (CountWrapper<T>) super.like(function, value, false);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public CountWrapper<T> like(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (CountWrapper<T>) super.like(function, value, isEmpty);
    }

    /**
     * 右模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public CountWrapper<T> likeRight(FuncUtils<T> function, Object value) {
        return (CountWrapper<T>) super.likeRight(function, value, false);
    }

    /**
     * 右模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public CountWrapper<T> likeRight(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (CountWrapper<T>) super.likeRight(function, value, isEmpty);
    }

    /**
     * 左模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public CountWrapper<T> likeLeft(FuncUtils<T> function, Object value) {
        return (CountWrapper<T>) super.likeLeft(function, value, false);
    }

    /**
     * 左模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public CountWrapper<T> likeLeft(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (CountWrapper<T>) super.likeLeft(function, value, isEmpty);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public CountWrapper<T> notLike(FuncUtils<T> function, Object value) {
        return (CountWrapper<T>) super.notLike(function, value, false);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public CountWrapper<T> notLike(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (CountWrapper<T>) super.notLike(function, value, isEmpty);
    }

    /**
     * 大于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public CountWrapper<T> gt(FuncUtils<T> function, Object value) {
        return (CountWrapper<T>) super.gt(function, value, false);
    }

    /**
     * 大于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public CountWrapper<T> gt(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (CountWrapper<T>) super.gt(function, value, isEmpty);
    }

    /**
     * 大于等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public CountWrapper<T> ge(FuncUtils<T> function, Object value) {
        return (CountWrapper<T>) super.ge(function, value, false);
    }

    /**
     * 大于等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public CountWrapper<T> ge(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (CountWrapper<T>) super.ge(function, value, isEmpty);
    }

    /**
     * 小于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public CountWrapper<T> lt(FuncUtils<T> function, Object value) {
        return (CountWrapper<T>) super.lt(function, value, false);
    }

    /**
     * 小于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public CountWrapper<T> lt(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (CountWrapper<T>) super.lt(function, value, isEmpty);
    }

    /**
     * 小于等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public CountWrapper<T> le(FuncUtils<T> function, Object value) {
        return (CountWrapper<T>) super.le(function, value, false);
    }

    /**
     * 小于等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public CountWrapper<T> le(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (CountWrapper<T>) super.le(function, value, isEmpty);
    }

    /**
     * 为空
     * @param function f
     * @return p
     */
    @Override
    public CountWrapper<T> isNull(FuncUtils<T> function) {
        return (CountWrapper<T>) super.isNull(function);
    }

    /**
     * 不为空
     * @param function f
     * @return p
     */
    @Override
    public CountWrapper<T> isNotNull(FuncUtils<T> function) {
        return (CountWrapper<T>) super.isNotNull(function);
    }

    /**
     * 拼接in 条件,参数拼接为属性名 fieldName_[0-n]
     * @param function f
     * @param value v
     * @return p
     */
    @Override
    public CountWrapper<T> in(FuncUtils<T> function, java.util.List<?> value){
        return (CountWrapper<T>) super.in(function, value);
    }

    /**
     * 拼接in 条件,参数拼接为属性名 fieldName_[0-n]
     * @param function f
     * @param value v
     * @return p
     */
    @Override
    public CountWrapper<T> in(FuncUtils<T> function, Object... value){
        return (CountWrapper<T>) super.in(function, value);
    }

    /**
     * 拼接SQL<br/>
     * 示例：(content like concat('%',#{keyWord}, '%') or version_num like concat('%', #{keyWord},'%'))
     * @param whereSQL where条件
     * @param param 参数
     * @return p
     */
    @Override
    public CountWrapper<T> jointSQL(String whereSQL, java.util.HashMap<String, Object> param){
        return (CountWrapper<T>) super.jointSQL(whereSQL, param);
    }

    /**
     * 拼接SQL<br/>
     * 示例：(content like concat('%',#{keyWord}, '%') or version_num like concat('%', #{keyWord},'%'))
     * @param whereSQL where条件;user_name = #{userName}
     * @param fieldName userName
     * @param value 参数值
     * @return WhereWrapper
     */
    @Override
    public CountWrapper<T> jointSQL(String whereSQL, String fieldName, Object value){
        return (CountWrapper<T>) super.jointSQL(whereSQL, fieldName, value);
    }

    /**
     * 或条件
     * @return p
     */
    @Override
    public CountWrapper<T> or() {
        return (CountWrapper<T>) super.or();
    }

    /**
     * 排序
     * @param columns 排序列名；示例：create_time desc
     * @return p
     */
    @Override
    public CountWrapper<T> orderBy(String... columns) {
        return (CountWrapper<T>) super.orderBy(columns);
    }

    /**
     * 设置页数
     * @param pageNum i
     * @return p
     */
    @Override
    public CountWrapper<T> pageNum(Integer pageNum) {
        return (CountWrapper<T>) super.pageNum(pageNum);
    }

    /**
     * 设置每页数
     * @param pageSize i
     * @return p
     */
    @Override
    public CountWrapper<T> pageSize(Integer pageSize) {
        return (CountWrapper<T>) super.pageSize(pageSize);
    }

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return p
     */
    @Override
    public CountWrapper<T> page(Integer pageNum, Integer pageSize) {
        return (CountWrapper<T>) super.page(pageNum, pageSize);
    }

}