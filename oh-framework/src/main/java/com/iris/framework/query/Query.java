package com.iris.framework.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 查询公共参数
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class Query {
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    //@Schema(description = "当前页码", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer page = 1;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数，取值范围 1-1000")
    @Max(value = 1000, message = "每页条数，取值范围 1-1000")
    //@Schema(description = "每页条数", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer limit = 10;

    //@Schema(description = "排序字段")
    String order;

    //@Schema(description = "是否升序")
    boolean asc;

    //@Schema(description = "过滤SQL")
    private String sqlFilter;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

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
