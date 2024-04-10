package com.iris.framework.datasource.interceptor;

/**
 * 数据范围
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class DataScope {
    private String sqlFilter;

    public DataScope(String sqlFilter) {
        this.sqlFilter = sqlFilter;
    }

    public String getSqlFilter() {
        return sqlFilter;
    }

    public void setSqlFilter(String sqlFilter) {
        this.sqlFilter = sqlFilter;
    }
}