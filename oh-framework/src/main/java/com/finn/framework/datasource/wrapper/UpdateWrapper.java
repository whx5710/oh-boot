package com.finn.framework.datasource.wrapper;

import com.finn.framework.aop.annotations.FuncUtils;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * SQL 修改语句构建器
 * @author 王小费
 * @since 2025-06-28
 */
public class UpdateWrapper<T> extends Wrapper<T> {

    /**
     * 更新人 Long
     */
    private static final String UPDATER = "updater";
    /**
     * 更新时间 LocalDateTime
     */
    private static final String UPDATE_TIME = "updateTime";

    /**
     * 初始化，构建SQL：update tableName
     * @param clazz
     * @param b 是否强制更新用户ID和时间
     * @return
     * @param <T>
     */
    public static <T> UpdateWrapper<T> of(Class<T> clazz, boolean b) {
        UpdateWrapper<T> params = new UpdateWrapper<>();
        SQL tmpSQL = new SQL();
        String tableName = getTableName(clazz);
        tmpSQL.UPDATE(tableName);

        // 拼接列名
        Map<String, String> colValue = buildColumn(clazz);
        params.setColValue(colValue); // 列名

        // 是否更新用户ID和时间
        if(b){
            // 更新人、更新时间
            if(colValue.containsKey(UPDATER)){
                UserDetail user = SecurityUser.getUser();
                if(user != null && user.getId() != null){
                    tmpSQL.SET(UPDATER + " = #{__SET" + UPDATER + "}");
                    params.put("__SET" + UPDATER, user.getId());
                }
            }
            if(colValue.containsKey(UPDATE_TIME)){
                tmpSQL.SET("update_time = #{__SET" + UPDATE_TIME + "}");
                params.put("__SET" + UPDATE_TIME, LocalDateTime.now());
            }
        }

        params.setSql(tmpSQL);
        return params;
    }

    /**
     * 初始化，默认更新用户ID和更新时间
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> UpdateWrapper<T> of(Class<T> clazz) {
        return of(clazz, true);
    }

    /**
     * 设置更新值
     * @param function f
     * @param value 值
     * @return p
     */
    public UpdateWrapper<T> set(FuncUtils<T> function, Object value) {
        String fieldName = com.finn.framework.utils.ReflectUtil.getFieldName(function);
        String colName = getColName(fieldName);
        fieldName = "__SET" + fieldName;
        this.getSql().SET(colName + " = #{" + fieldName + "}");
        this.put(fieldName, value);
        return this;
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public UpdateWrapper<T> eq(FuncUtils<T> function, Object value) {
        return (UpdateWrapper<T>) super.eq(function, value, false);
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public UpdateWrapper<T> eq(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (UpdateWrapper<T>) super.eq(function, value, isEmpty);
    }

    /**
     * 不等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public UpdateWrapper<T> ne(FuncUtils<T> function, Object value) {
        return (UpdateWrapper<T>) super.ne(function, value, false);
    }

    /**
     * 不等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public UpdateWrapper<T> ne(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (UpdateWrapper<T>) super.ne(function, value, isEmpty);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public UpdateWrapper<T> like(FuncUtils<T> function, Object value) {
        return (UpdateWrapper<T>) super.like(function, value, false);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public UpdateWrapper<T> like(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (UpdateWrapper<T>) super.like(function, value, isEmpty);
    }

    /**
     * 右模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public UpdateWrapper<T> likeRight(FuncUtils<T> function, Object value) {
        return (UpdateWrapper<T>) super.likeRight(function, value, false);
    }

    /**
     * 右模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public UpdateWrapper<T> likeRight(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (UpdateWrapper<T>) super.likeRight(function, value, isEmpty);
    }

    /**
     * 左模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public UpdateWrapper<T> likeLeft(FuncUtils<T> function, Object value) {
        return (UpdateWrapper<T>) super.likeLeft(function, value, false);
    }

    /**
     * 左模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public UpdateWrapper<T> likeLeft(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (UpdateWrapper<T>) super.likeLeft(function, value, isEmpty);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public UpdateWrapper<T> notLike(FuncUtils<T> function, Object value) {
        return (UpdateWrapper<T>) super.notLike(function, value, false);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public UpdateWrapper<T> notLike(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (UpdateWrapper<T>) super.notLike(function, value, isEmpty);
    }

    /**
     * 大于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public UpdateWrapper<T> gt(FuncUtils<T> function, Object value) {
        return (UpdateWrapper<T>) super.gt(function, value, false);
    }

    /**
     * 大于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public UpdateWrapper<T> gt(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (UpdateWrapper<T>) super.gt(function, value, isEmpty);
    }

    /**
     * 大于等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public UpdateWrapper<T> ge(FuncUtils<T> function, Object value) {
        return (UpdateWrapper<T>) super.ge(function, value, false);
    }

    /**
     * 大于等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public UpdateWrapper<T> ge(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (UpdateWrapper<T>) super.ge(function, value, isEmpty);
    }

    /**
     * 小于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public UpdateWrapper<T> lt(FuncUtils<T> function, Object value) {
        return (UpdateWrapper<T>) super.lt(function, value, false);
    }

    /**
     * 小于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public UpdateWrapper<T> lt(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (UpdateWrapper<T>) super.lt(function, value, isEmpty);
    }

    /**
     * 小于等于
     * @param function f
     * @param value 值
     * @return p
     */
    @Override
    public UpdateWrapper<T> le(FuncUtils<T> function, Object value) {
        return (UpdateWrapper<T>) super.le(function, value, false);
    }

    /**
     * 小于等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    @Override
    public UpdateWrapper<T> le(FuncUtils<T> function, Object value, Boolean isEmpty) {
        return (UpdateWrapper<T>) super.le(function, value, isEmpty);
    }

    /**
     * 为空
     * @param function f
     * @return p
     */
    @Override
    public UpdateWrapper<T> isNull(FuncUtils<T> function) {
        return (UpdateWrapper<T>) super.isNull(function);
    }

    /**
     * 不为空
     * @param function f
     * @return p
     */
    @Override
    public UpdateWrapper<T> isNotNull(FuncUtils<T> function) {
        return (UpdateWrapper<T>) super.isNotNull(function);
    }

    /**
     * 拼接in 条件,参数拼接为属性名 fieldName_[0-n]
     * @param function f
     * @param value v
     * @return p
     */
    @Override
    public UpdateWrapper<T> in(FuncUtils<T> function, java.util.List<?> value){
        return (UpdateWrapper<T>) super.in(function, value);
    }

    /**
     * 拼接in 条件,参数拼接为属性名 fieldName_[0-n]
     * @param function f
     * @param value v
     * @return p
     */
    @Override
    public UpdateWrapper<T> in(FuncUtils<T> function, Object... value){
        return (UpdateWrapper<T>) super.in(function, value);
    }

    /**
     * 拼接SQL<br/>
     * 示例：(content like concat('%',#{keyWord}, '%') or version_num like concat('%', #{keyWord},'%'))
     * @param whereSQL where条件
     * @param param 参数
     * @return p
     */
    @Override
    public UpdateWrapper<T> jointSQL(String whereSQL, java.util.HashMap<String, Object> param){
        return (UpdateWrapper<T>) super.jointSQL(whereSQL, param);
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
    public UpdateWrapper<T> jointSQL(String whereSQL, String fieldName, Object value){
        return (UpdateWrapper<T>) super.jointSQL(whereSQL, fieldName, value);
    }

    /**
     * 或条件
     * @return p
     */
    @Override
    public UpdateWrapper<T> or() {
        return (UpdateWrapper<T>) super.or();
    }

    /**
     * 排序
     * @param columns 排序列名；示例：create_time desc
     * @return p
     */
    @Override
    public UpdateWrapper<T> orderBy(String... columns) {
        return (UpdateWrapper<T>) super.orderBy(columns);
    }

    /**
     * 设置页数
     * @param pageNum i
     * @return p
     */
    @Override
    public UpdateWrapper<T> pageNum(Integer pageNum) {
        return (UpdateWrapper<T>) super.pageNum(pageNum);
    }

    /**
     * 设置每页数
     * @param pageSize i
     * @return p
     */
    @Override
    public UpdateWrapper<T> pageSize(Integer pageSize) {
        return (UpdateWrapper<T>) super.pageSize(pageSize);
    }

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return p
     */
    @Override
    public UpdateWrapper<T> page(Integer pageNum, Integer pageSize) {
        return (UpdateWrapper<T>) super.page(pageNum, pageSize);
    }

}