package com.iris.sys.base.query;

import com.iris.framework.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 菜单查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "菜单查询")
public class SysMenuQuery extends Query {
    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "类型   0：菜单   1：按钮   2：接口")
    private Integer type;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
