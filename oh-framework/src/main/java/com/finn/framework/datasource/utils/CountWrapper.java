package com.finn.framework.datasource.utils;

import org.apache.ibatis.jdbc.SQL;

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

}
