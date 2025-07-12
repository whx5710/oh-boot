package com.finn.framework.datasource.utils;

import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;

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
        HashMap<String, String> colValue = buildColumn(clazz);
        params.setColValue(colValue); // 列名
        return params;
    }
}
