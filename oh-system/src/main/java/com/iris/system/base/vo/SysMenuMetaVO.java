package com.iris.system.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

import java.io.Serializable;

/**
 * 菜单元素
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "菜单元素")
public class SysMenuMetaVO implements Serializable {

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "菜单名称")
    private String title;

    @Schema(description = "排序")
    @Min(value = 0, message = "排序值不能小于0")
    private Integer rank;

    @Schema(description = "授权标识")
    private String [] auths;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public @Min(value = 0, message = "排序值不能小于0") Integer getRank() {
        return rank;
    }

    public void setRank(@Min(value = 0, message = "排序值不能小于0") Integer rank) {
        this.rank = rank;
    }

    public String[] getAuths() {
        return auths;
    }

    public void setAuths(String[] auths) {
        this.auths = auths;
    }
}
