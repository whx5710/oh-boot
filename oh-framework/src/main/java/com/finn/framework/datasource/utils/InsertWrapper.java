package com.finn.framework.datasource.utils;

import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;

/**
 * SQL 新增语句构建器<br/>
 * @author 王小费
 * @since 2025-06-29
 */
public class InsertWrapper extends HashMap<String, Object>{

    private SQL sql;
    /**
     * 初始化，构建SQL：insert into tableName
     * @param tableName 表名
     * @return
     */
    public static InsertWrapper of(String tableName) {
        InsertWrapper params = new InsertWrapper();
        SQL tmpSQL = new SQL();
        tmpSQL.INSERT_INTO(tableName);
        params.setSql(tmpSQL);
        return params;
    }

    /**
     * 插入数值
     * @param columns 数据库列名
     * @param values 数据
     * @return
     */
    public InsertWrapper values(String columns, Object values){
        sql.VALUES(columns, "#{" + columns + "}");
        this.put(columns, values);
        return this;
    }

    public SQL getSql() {
        return sql;
    }

    public void setSql(SQL sql) {
        this.sql = sql;
    }
}
