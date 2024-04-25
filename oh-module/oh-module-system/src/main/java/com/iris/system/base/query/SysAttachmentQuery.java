package com.iris.system.base.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.iris.framework.common.query.Query;

import java.util.Objects;

/**
 * 附件管理查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "附件管理查询")
public class SysAttachmentQuery extends Query {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysAttachmentQuery that)) return false;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getPlatform(), that.getPlatform());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPlatform());
    }
}