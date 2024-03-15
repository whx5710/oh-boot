package com.iris.system.pim.query;

import com.iris.framework.common.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
* 短信日志查询
*
* @author 王小费 whx5710@qq.com
*/
@Schema(description = "短信日志查询")
public class SmsLogQuery extends Query {
    @Schema(description = "平台ID")
    private Long platformId;

    @Schema(description = "平台类型")
    private Integer platform;

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }
}