package com.iris.sys.base.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.iris.framework.common.query.Query;

/**
 * 角色管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Schema(description = "角色查询")
public class SysRoleQuery extends Query {
    @Schema(description = "角色名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
