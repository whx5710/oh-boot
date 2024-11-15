package com.iris.sys.base.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.iris.framework.query.Query;

import java.util.Objects;

/**
 * 岗位管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Schema(description = "岗位管理查询")
public class SysPostQuery extends Query {
    @Schema(description = "岗位编码")
    private String postCode;

    @Schema(description = "岗位名称")
    private String postName;

    @Schema(description = "状态  0：停用   1：正常")
    private Integer status;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysPostQuery that)) return false;
        return Objects.equals(getPostCode(), that.getPostCode()) && Objects.equals(getPostName(), that.getPostName()) && Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostCode(), getPostName(), getStatus());
    }
}
