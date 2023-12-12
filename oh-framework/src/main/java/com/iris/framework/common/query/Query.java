package com.iris.framework.common.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

/**
 * 查询公共参数
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class Query {
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    @Schema(description = "当前页码", required = true)
    Integer page;

    @NotNull(message = "每页条数不能为空")
    @Range(min = 1, max = 1000, message = "每页条数，取值范围 1-1000")
    @Schema(description = "每页条数", required = true)
    Integer limit;

    @Schema(description = "排序字段")
    String order;

    @Schema(description = "是否升序")
    boolean asc;

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
}
