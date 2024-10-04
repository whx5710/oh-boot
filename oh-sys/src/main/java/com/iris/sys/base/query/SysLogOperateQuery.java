package com.iris.sys.base.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.iris.framework.common.query.Query;

import java.util.Objects;

/**
 * 操作日志查询
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Schema(description = "操作日志查询")
public class SysLogOperateQuery extends Query {
    @Schema(description = "用户")
    private String realName;

    @Schema(description = "模块名")
    private String module;

    @Schema(description = "请求URI")
    private String reqUri;

    @Schema(description = "操作状态")
    private Integer status;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
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
        if (!(o instanceof SysLogOperateQuery that)) return false;
        return Objects.equals(getRealName(), that.getRealName()) && Objects.equals(getModule(), that.getModule()) && Objects.equals(getReqUri(), that.getReqUri()) && Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRealName(), getModule(), getReqUri(), getStatus());
    }
}