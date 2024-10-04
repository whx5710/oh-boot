package com.iris.sys.base.query;

import com.iris.framework.common.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
* 短信平台查询
*
* @author 王小费 whx5710@qq.com
*/
@Schema(description = "短信平台查询")
public class SmsPlatformQuery extends Query {
    @Schema(description = "平台类型  0：阿里云   1：腾讯云")
    private Integer platform;

    @Schema(description = "短信签名")
    private String signName;

    /**
     * 状态  0：禁用   1：启用
     */
    @Schema(description = "状态  0：禁用   1：启用")
    private Integer status;

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}