package com.iris.support.query;

import com.iris.framework.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 机构查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "机构查询")
public class SysOrgQuery extends Query {
    @Schema(description = "机构名称")
    private String name;

    @Schema(description = "父级ID")
    private Long parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
