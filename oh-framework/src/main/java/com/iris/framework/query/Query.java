package com.iris.framework.query;

/**
 * 查询公共参数
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class Query extends PageFilter<Query>{

    //@Schema(description = "排序字段")
    String order;

    //@Schema(description = "是否升序")
    boolean asc;

    //@Schema(description = "过滤SQL")
    private String sqlFilter;

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
