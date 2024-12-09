package com.iris.framework.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 查询分页参数
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class PageFilter<T>{

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    Integer page = 1; // 当前页码

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数，取值范围 1-10000")
    @Max(value = 10000, message = "每页条数，取值范围 1-10000")
    Integer limit = 10; // 每页条数

    // 总条数
    Long total;

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
}
