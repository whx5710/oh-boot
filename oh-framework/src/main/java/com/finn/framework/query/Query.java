package com.finn.framework.query;

import com.github.pagehelper.PageParam;

/**
 * 查询参数
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class Query extends PageParam {

    /**
     * 过滤SQL
     */
    private String sqlFilter;

    /**
     * 总数
     */
    private Long total = 0L;

    public String getSqlFilter() {
        return sqlFilter;
    }

    public void setSqlFilter(String sqlFilter) {
        this.sqlFilter = sqlFilter;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
