package com.finn.framework.datasource.wrapper;

import com.finn.framework.aop.annotations.FuncUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * SQL 删除语句构建器<br/>
 * @author 王小费
 * @since 2025-06-28
 */
public class DeleteWrapper<T> extends Wrapper<T> {

    /**
     * 初始化，构建SQL：delete from tableName
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> DeleteWrapper<T> of(Class<T> clazz) {
        DeleteWrapper<T> params = new DeleteWrapper<>();
        SQL tmpSQL = new SQL();
        String tableName = getTableName(clazz);
        tmpSQL.DELETE_FROM(tableName);
        params.setSql(tmpSQL);
        // 拼接列名
        params.setColValue(buildColumn(clazz));
        return params;
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public DeleteWrapper<T> eq(FuncUtils<T> function, Object value) {
        return (DeleteWrapper<T>) super.eq(function, value, false);
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public DeleteWrapper<T> eq(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (DeleteWrapper<T>) super.eq(function, value, isEmpty);
    }

    /**
     * 不等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public DeleteWrapper<T> ne(FuncUtils<T> function, Object value) {
        return (DeleteWrapper<T>) super.ne(function, value, false);
    }

    /**
     * 不等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public DeleteWrapper<T> ne(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (DeleteWrapper<T>) super.ne(function, value, isEmpty);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public DeleteWrapper<T> like(FuncUtils<T> function, Object value) {
        return (DeleteWrapper<T>) super.like(function, value, false);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public DeleteWrapper<T> like(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (DeleteWrapper<T>) super.like(function, value, isEmpty);
    }

    /**
     * 右模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public DeleteWrapper<T> likeRight(FuncUtils<T> function, Object value) {
        return (DeleteWrapper<T>) super.likeRight(function, value, false);
    }

    /**
     * 右模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public DeleteWrapper<T> likeRight(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (DeleteWrapper<T>) super.likeRight(function, value, isEmpty);
    }

    /**
     * 左模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public DeleteWrapper<T> likeLeft(FuncUtils<T> function, Object value) {
        return (DeleteWrapper<T>) super.likeLeft(function, value, false);
    }

    /**
     * 左模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public DeleteWrapper<T> likeLeft(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (DeleteWrapper<T>) super.likeLeft(function, value, isEmpty);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public DeleteWrapper<T> notLike(FuncUtils<T> function, Object value) {
        return (DeleteWrapper<T>) super.notLike(function, value, false);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public DeleteWrapper<T> notLike(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (DeleteWrapper<T>) super.notLike(function, value, isEmpty);
    }

    /**
     * 大于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public DeleteWrapper<T> gt(FuncUtils<T> function, Object value) {
        return (DeleteWrapper<T>) super.gt(function, value, false);
    }

    /**
     * 大于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public DeleteWrapper<T> gt(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (DeleteWrapper<T>) super.gt(function, value, isEmpty);
    }

    /**
     * 大于等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public DeleteWrapper<T> ge(FuncUtils<T> function, Object value) {
        return (DeleteWrapper<T>) super.ge(function, value, false);
    }

    /**
     * 大于等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public DeleteWrapper<T> ge(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (DeleteWrapper<T>) super.ge(function, value, isEmpty);
    }

    /**
     * 小于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public DeleteWrapper<T> lt(FuncUtils<T> function, Object value) {
        return (DeleteWrapper<T>) super.lt(function, value, false);
    }

    /**
     * 小于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public DeleteWrapper<T> lt(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (DeleteWrapper<T>) super.lt(function, value, isEmpty);
    }

    /**
     * 小于等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public DeleteWrapper<T> le(FuncUtils<T> function, Object value) {
        return (DeleteWrapper<T>) super.le(function, value, false);
    }

    /**
     * 小于等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public DeleteWrapper<T> le(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (DeleteWrapper<T>) super.le(function, value, isEmpty);
    }

    /**
     * 为空
     * @param function f
     * @return p
     */
    @Override
    public DeleteWrapper<T> isNull(FuncUtils<T> function) {
        return (DeleteWrapper<T>) super.isNull(function);
    }

    /**
     * 不为空
     * @param function f
     * @return p
     */
    @Override
    public DeleteWrapper<T> isNotNull(FuncUtils<T> function) {
        return (DeleteWrapper<T>) super.isNotNull(function);
    }

    /**
     * 拼接in 条件,参数拼接为属性名 fieldName_[0-n]
     * @param function f
     * @param value v
     * @return p
     */
    @Override
    public DeleteWrapper<T> in(FuncUtils<T> function, java.util.List<?> value){
        return (DeleteWrapper<T>) super.in(function, value);
    }

    /**
     * 拼接in 条件,参数拼接为属性名 fieldName_[0-n]
     * @param function f
     * @param value v
     * @return p
     */
    @Override
    public DeleteWrapper<T> in(FuncUtils<T> function, Object... value){
        return (DeleteWrapper<T>) super.in(function, value);
    }

    /**
     * 拼接SQL<br/>
     * 示例：(content like concat('%',#{keyWord}, '%') or version_num like concat('%', #{keyWord},'%'))
     * @param whereSQL where条件
     * @param param 参数
     * @return p
     */
    @Override
    public DeleteWrapper<T> jointSQL(String whereSQL, java.util.HashMap<String, Object> param){
        return (DeleteWrapper<T>) super.jointSQL(whereSQL, param);
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
    public DeleteWrapper<T> jointSQL(String whereSQL, String fieldName, Object value){
        return (DeleteWrapper<T>) super.jointSQL(whereSQL, fieldName, value);
    }

    /**
     * 或条件
     * @return p
     */
    @Override
    public DeleteWrapper<T> or() {
        return (DeleteWrapper<T>) super.or();
    }

    /**
     * 排序
     * @param columns 排序列名；示例：create_time desc
     * @return p
     */
    @Override
    public DeleteWrapper<T> orderBy(String... columns) {
        return (DeleteWrapper<T>) super.orderBy(columns);
    }

    /**
     * 排序，默认顺序 asc
     * @param function User::getCreateTime
     * @return w
     */
    @Override
    public QueryWrapper<T> orderBy(FuncUtils<T> function) {
        return (QueryWrapper<T>) super.orderBy(function);
    }

    /**
     * 设置页数
     * @param pageNum i
     * @return p
     */
    @Override
    public DeleteWrapper<T> pageNum(Integer pageNum) {
        return (DeleteWrapper<T>) super.pageNum(pageNum);
    }

    /**
     * 设置每页数
     * @param pageSize i
     * @return p
     */
    @Override
    public DeleteWrapper<T> pageSize(Integer pageSize) {
        return (DeleteWrapper<T>) super.pageSize(pageSize);
    }

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return p
     */
    @Override
    public DeleteWrapper<T> page(Integer pageNum, Integer pageSize) {
        return (DeleteWrapper<T>) super.page(pageNum, pageSize);
    }
}
