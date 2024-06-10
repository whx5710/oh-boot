package com.iris.system.base.vo;

import com.iris.framework.common.utils.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * 菜单管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "菜单")
public class SysMenuNativeVO extends TreeNode<SysMenuNativeVO> {

    @Schema(description = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @Schema(description = "菜单URL")
    private String path;

    @Schema(description = "类型  0：菜单   1：按钮   2：接口")
    @Range(min = 0, max = 2, message = "类型不正确")
    private Integer type;

    @Schema(description = "打开方式   0：内部   1：外部")
    @Range(min = 0, max = 1, message = "打开方式不正确")
    private Integer openStyle;

    // 菜单元素
    private SysMenuMetaVO meta;

    public @NotBlank(message = "菜单名称不能为空") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "菜单名称不能为空") String name) {
        this.name = name;
    }

    public @Range(min = 0, max = 2, message = "类型不正确") Integer getType() {
        return type;
    }

    public void setType(@Range(min = 0, max = 2, message = "类型不正确") Integer type) {
        this.type = type;
    }

    public @Range(min = 0, max = 1, message = "打开方式不正确") Integer getOpenStyle() {
        return openStyle;
    }

    public void setOpenStyle(@Range(min = 0, max = 1, message = "打开方式不正确") Integer openStyle) {
        this.openStyle = openStyle;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SysMenuMetaVO getMeta() {
        return meta;
    }

    public void setMeta(SysMenuMetaVO meta) {
        this.meta = meta;
    }
}
