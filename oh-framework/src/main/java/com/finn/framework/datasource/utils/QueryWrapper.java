package com.finn.framework.datasource.utils;

import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;

/**
 * SQL 查询语句构建器<br/>
 * 参数构建类；支持常用的单表查询语句构建
 * @author 王小费
 * @since 2025-03-12
 */
public class QueryWrapper<T> extends Wrapper<T> {
    /**
     * 初始化,构建SQL：select 列名 from tableName
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> QueryWrapper<T> of(Class<T> clazz) {
        QueryWrapper<T> params = new QueryWrapper<>();
        SQL tmpSQL = new SQL();
        // 拼接列名
        HashMap<String, String> colValue = buildQueryColumn(tmpSQL, clazz);
        params.setSql(tmpSQL);
        params.setColValue(colValue); // 列名
        String tableName = getTableName(clazz);
        params.getSql().FROM(tableName); // 表名
        return params;
    }

}
