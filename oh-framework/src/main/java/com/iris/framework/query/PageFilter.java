package com.iris.framework.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PageFilter<T>{

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    //@Schema(description = "当前页码", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer page = 1;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数，取值范围 1-10000")
    @Max(value = 10000, message = "每页条数，取值范围 1-10000")
    //@Schema(description = "每页条数", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer limit = 10;

    // 总条数
    Long total;

    private List<T> data;

    public @NotNull(message = "页码不能为空") @Min(value = 1, message = "页码最小值为 1") Integer getPage() {
        return page;
    }

    public void setPage(@NotNull(message = "页码不能为空") @Min(value = 1, message = "页码最小值为 1") Integer page) {
        this.page = page;
    }

    public @NotNull(message = "每页条数不能为空") @Min(value = 1, message = "每页条数，取值范围 1-10000") @Max(value = 10000, message = "每页条数，取值范围 1-10000") Integer getLimit() {
        return limit;
    }

    public void setLimit(@NotNull(message = "每页条数不能为空") @Min(value = 1, message = "每页条数，取值范围 1-10000") @Max(value = 10000, message = "每页条数，取值范围 1-10000") Integer limit) {
        this.limit = limit;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @param obj 继承了PageFilter类的子类
     * @param response 从数据库查询出来的参数
     * @return pages
     */
    public PageFilter<T> getPageFilter(PageFilter<T> obj, List<T> response) {
        PageFilter<T> pages = new PageFilter<>();
        pages.setPage(obj.getPage());
        pages.setLimit(obj.getLimit());
        pages.setTotal(obj.getTotal());
        pages.setData(response);
        return pages;
    }
}
