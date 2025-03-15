package com.finn.sys.base.query;

import com.finn.framework.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 附件管理查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "附件管理查询")
public class AttachmentQuery extends Query {
    @Schema(description = "附件名称")
    private String name;

    @Schema(description = "存储平台")
    private String platform;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}