package com.finn.framework.datasource.wrapper;

import com.finn.framework.aop.annotations.FuncUtils;
import com.finn.framework.aop.annotations.TableField;
import com.finn.framework.aop.annotations.TableId;
import com.finn.framework.aop.annotations.TableName;
import com.finn.framework.exception.ServerException;
import com.finn.framework.utils.ReflectUtil;
import com.finn.framework.utils.Tools;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.finn.framework.common.constant.Constant.PAGE_NUM;
import static com.finn.framework.common.constant.Constant.PAGE_SIZE;

/**
 * SQL 删除语句构建器<br/>
 * @author 王小费
 * @since 2025-06-28
 */
public class DeleteWrapper<T> extends HashMap<String, Object> {

    private final static Logger log = LoggerFactory.getLogger(DeleteWrapper.class);
    // 字段缓存，减少反射开销
    private static final Map<Class<?>, List<Field>> FIELD_CACHE = new ConcurrentHashMap<>();
    // 表名缓存
    private static final Map<Class<?>, String> TABLE_NAME_CACHE = new ConcurrentHashMap<>();

    // sql构建器
    private SQL sql;

    public SQL getSql() {
        return sql;
    }

    public void setSql(SQL sql) {
        this.sql = sql;
    }

    // 列名，使用线程安全的ConcurrentHashMap
    protected ConcurrentHashMap<String, String> colValue;
    
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
        Map<String, String> colValue = buildColumn(clazz);
        params.setColValue(colValue); // 列名
        return params;
    }

    // 缓存列名
    public void setColValue(Map<String, String> colValue){
        this.colValue = new ConcurrentHashMap<>(colValue);
    }

    /**
     * 根据字段属性名，获取列名
     * @param fieldName 字段名
     * @return 列名
     */
    protected String getColName(String fieldName){
        if(colValue.containsKey(fieldName)){
            return colValue.get(fieldName);
        }else{
            throw new ServerException("【" + fieldName + "】字段不存在，请检查");
        }
    }

    /**
     * 获取表名
     * @param clazz 实体类
     * @return 表名
     */
    public static String getTableName(Class<?> clazz){
        return TABLE_NAME_CACHE.computeIfAbsent(clazz, c -> {
            // 获取表名
            TableName apoTable = c.getAnnotation(TableName.class);
            if(apoTable == null){
                String s = c.getName();
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
        });
    }

    /**
     * 获取主键
     * @param clazz 实体类
     * @return 主键名
     */
    public static String getPriKey(Class<?> clazz){
        List<Field> fields = getCachedFields(clazz);
        for(Field field: fields){
            if (field.isAnnotationPresent(TableId.class)) { // 判断是否有该注解
                TableId annotation = field.getAnnotation(TableId.class);
                return annotation.value();
            }
        }
        return "id";
    }

    /**
     * 获取缓存的字段列表
     * @param clazz 实体类
     * @return 字段列表
     */
    protected static List<Field> getCachedFields(Class<?> clazz) {
        return FIELD_CACHE.computeIfAbsent(clazz, ReflectUtil::getFields);
    }

    /**
     * 组装列名，缓存列名
     *
     * @param clazz 实体类
     * @return 列名映射
     */
    protected static Map<String, String> buildColumn(Class<?> clazz){
        return buildColumn(clazz, null);
    }

    /**
     * 组装列名，缓存列名
     *
     * @param sql SQL构建器
     * @param clazz 实体类
     * @return 列名映射
     */
    protected static Map<String, String> buildQueryColumn(SQL sql, Class<?> clazz){
        return buildColumn(clazz, sql);
    }

    /**
     * 组装列名，缓存列名
     *
     * @param clazz 实体类
     * @param sql SQL构建器
     * @return 列名映射
     */
    private static Map<String, String> buildColumn(Class<?> clazz, SQL sql){
        Map<String, String> colValue = new HashMap<>();
        List<Field> fields = getCachedFields(clazz);
        Map<String, Boolean> judge = new HashMap<>();
        for(Field field: fields){
            if (field.isAnnotationPresent(TableField.class)) { // 判断是否有该注解
                TableField annotation = field.getAnnotation(TableField.class);
                if (annotation.exists()) { // 剔除非数据库字段
                    if((judge.containsKey(field.getName()) && !judge.get(field.getName())) ||
                            (judge.containsKey(annotation.value()) && !judge.get(annotation.value()))){
                        log.warn("{} 子类有覆盖 {} 字段，不查询该字段", clazz.getName(), annotation.value());
                    }else{
                        if(sql != null) {
                            sql.SELECT(annotation.value());
                        }
                        colValue.put(field.getName(), annotation.value()); // 缓存列名
                    }
                }else{
                    // 如果子类覆盖了父类的属性，存在 exists = false的情况
                    String key = annotation.value()==null?field.getName():annotation.value();
                    if(key.isEmpty()){
                        key = field.getName();
                    }
                    judge.put(key, false);
                }
            }else{
                if(sql != null) {
                    sql.SELECT(field.getName());
                }
                colValue.put(field.getName(), field.getName()); // 缓存列名
            }
        }
        return colValue;
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @return p
     */
    public DeleteWrapper<T> eq(FuncUtils<T> function, Object value) {
        return eq(function, value, false);
    }

    /**
     * 等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public DeleteWrapper<T> eq(FuncUtils<T> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = ReflectUtil.getFieldName(function);
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
    public DeleteWrapper<T> ne(FuncUtils<T> function, Object value) {
        return ne(function, value, false);
    }

    /**
     * 不等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public DeleteWrapper<T> ne(FuncUtils<T> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = ReflectUtil.getFieldName(function);
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
    public DeleteWrapper<T> like(FuncUtils<T> function, Object value) {
        return like(function, value, false);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public DeleteWrapper<T> like(FuncUtils<T> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = ReflectUtil.getFieldName(function);
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
    public DeleteWrapper<T> likeRight(FuncUtils<T> function, Object value) {
        return likeRight(function, value, false);
    }

    /**
     * 右模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public DeleteWrapper<T> likeRight(FuncUtils<T> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = ReflectUtil.getFieldName(function);
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
    public DeleteWrapper<T> likeLeft(FuncUtils<T> function, Object value) {
        return likeLeft(function, value, false);
    }

    /**
     * 左模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public DeleteWrapper<T> likeLeft(FuncUtils<T> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = ReflectUtil.getFieldName(function);
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
    public DeleteWrapper<T> notLike(FuncUtils<T> function, Object value) {
        return notLike(function, value, false);
    }

    /**
     * 模糊查询
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public DeleteWrapper<T> notLike(FuncUtils<T> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = ReflectUtil.getFieldName(function);
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
    public DeleteWrapper<T> gt(FuncUtils<T> function, Object value) {
        return gt(function, value, false);
    }

    /**
     * 大于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public DeleteWrapper<T> gt(FuncUtils<T> function, Object value, Boolean isEmpty) {
        if(value != null){
            String prefix = "__GT";
            String fieldName = ReflectUtil.getFieldName(function);
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " > #{" + prefix + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " > #{" + prefix + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " > #{" + prefix + fieldName + "}");
            }
            this.put(prefix + fieldName, value);
        }
        return this;
    }

    /**
     * 大于等于
     * @param function f
     * @param value 值
     * @return p
     */
    public DeleteWrapper<T> ge(FuncUtils<T> function, Object value) {
        return ge(function, value, false);
    }

    /**
     * 大于等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public DeleteWrapper<T> ge(FuncUtils<T> function, Object value, Boolean isEmpty) {
        if(value != null){
            String prefix = "Ge";
            String fieldName = ReflectUtil.getFieldName(function);
            String colName = getColName(fieldName);
            if(value instanceof String str){
                if(str.isEmpty()){
                    if(isEmpty){
                        sql.WHERE(colName + " >= #{" + prefix + fieldName + "}");
                    }
                }else{
                    sql.WHERE(colName + " >= #{" + prefix + fieldName + "}");
                }
            }else{
                sql.WHERE(colName + " >= #{" + prefix + fieldName + "}");
            }
            this.put(prefix + fieldName, value);
        }
        return this;
    }

    /**
     * 小于
     * @param function f
     * @param value 值
     * @return p
     */
    public DeleteWrapper<T> lt(FuncUtils<T> function, Object value) {
        return lt(function, value, false);
    }

    /**
     * 小于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public DeleteWrapper<T> lt(FuncUtils<T> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = ReflectUtil.getFieldName(function);
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
    public DeleteWrapper<T> le(FuncUtils<T> function, Object value) {
        return le(function, value, false);
    }

    /**
     * 小于等于
     * @param function f
     * @param value 值
     * @param isEmpty 是否允许为空字符串
     * @return p
     */
    public DeleteWrapper<T> le(FuncUtils<T> function, Object value, Boolean isEmpty) {
        if(value != null){
            String fieldName = ReflectUtil.getFieldName(function);
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
    public DeleteWrapper<T> isNull(FuncUtils<T> function) {
        String fieldName = ReflectUtil.getFieldName(function);
        String colName = getColName(fieldName);
        sql.WHERE(colName + " is null");
        return this;
    }

    /**
     * 不为空
     * @param function f
     * @return p
     */
    public DeleteWrapper<T> isNotNull(FuncUtils<T> function) {
        String fieldName = ReflectUtil.getFieldName(function);
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
    public DeleteWrapper<T> in(FuncUtils<T> function, List<?> value){
        if(value != null && !value.isEmpty()){
            String fieldName = ReflectUtil.getFieldName(function);
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
    public DeleteWrapper<T> in(FuncUtils<T> function, Object... value){
        if(value != null){
            String fieldName = ReflectUtil.getFieldName(function);
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
     * 示例：(content like concat('%',#{keyWord}, '%') or version_num like concat('%', #{keyWord},'%'))
     * @param whereSQL where条件
     * @param param 参数
     * @return p
     */
    public DeleteWrapper<T> jointSQL(String whereSQL, HashMap<String, Object> param){
        if(param != null && !param.isEmpty()){
            sql.WHERE(whereSQL);
            this.putAll(param);
        }
        return this;
    }

    /**
     * 拼接SQL<br/>
     * 示例：(content like concat('%',#{keyWord}, '%') or version_num like concat('%', #{keyWord},'%'))
     * @param whereSQL where条件;user_name = #{userName}
     * @param fieldName userName
     * @param value 参数值
     * @return WhereWrapper
     */
    public DeleteWrapper<T> jointSQL(String whereSQL, String fieldName, Object value){
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
    public DeleteWrapper<T> or() {
        sql.OR();
        return this;
    }

    /**
     * 排序
     * @param columns 排序列名；示例：create_time desc
     * @return p
     */
    public DeleteWrapper<T> orderBy(String... columns) {
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
    public DeleteWrapper<T> pageNum(Integer pageNum) {
        if(pageNum != null && pageNum > 0){
            this.put(PAGE_NUM, pageNum);
        }
        return this;
    }

    /**
     * 设置每页数
     * @param pageSize i
     * @return p
     */
    public DeleteWrapper<T> pageSize(Integer pageSize) {
        if(pageSize != null && pageSize > 0){
            this.put(PAGE_SIZE, pageSize);
        }
        return this;
    }

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return p
     */
    public DeleteWrapper<T> page(Integer pageNum, Integer pageSize) {
        if(pageNum != null && pageNum > 0){
            this.put(PAGE_NUM, pageNum);
        }
        if(pageSize != null && pageSize > 0){
            this.put(PAGE_SIZE, pageSize);
        }
        return this;
    }
}
