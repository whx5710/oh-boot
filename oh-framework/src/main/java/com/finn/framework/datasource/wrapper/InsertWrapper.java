package com.finn.framework.datasource.wrapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * SQL 新增语句构建器<br/>
 * @author 王小费
 * @since 2025-06-29
 */
@Deprecated
public class InsertWrapper extends Wrapper<Object> {

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
     * 初始化，构建SQL：insert into tableName
     * @param clazz 类
     * @return
     */
    public static <T> InsertWrapper of(Class<T> clazz) {
        InsertWrapper params = new InsertWrapper();
        SQL tmpSQL = new SQL();
        tmpSQL.INSERT_INTO(getTableName(clazz));
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
        getSql().VALUES(columns, "#{" + columns + "}");
        this.put(columns, values);
        return this;
    }

}