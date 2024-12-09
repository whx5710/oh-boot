package com.iris.framework.query;

/**
 * 查询参数
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class Query extends PageFilter<Query>{

    String order; // 排序字段

    boolean asc; // 是否升序

    private String sqlFilter; // 过滤SQL

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public String getSqlFilter() {
        return sqlFilter;
    }

    public void setSqlFilter(String sqlFilter) {
        this.sqlFilter = sqlFilter;
    }
}
