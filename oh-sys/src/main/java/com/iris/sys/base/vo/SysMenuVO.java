package com.iris.sys.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iris.framework.entity.IDEntity;
import com.iris.framework.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "用户")
public class SysMenuVO extends IDEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @Schema(description = "上级ID，一级菜单")
    private Long parentId;

    @Schema(description = "菜单URL")
    private String url;

    @Schema(description = "类型  0：菜单   1：按钮   2：接口")
    @Range(min = 0, max = 2, message = "类型不正确")
    private Integer type;

    @Schema(description = "打开方式   0：内部   1：外部")
    @Range(min = 0, max = 1, message = "打开方式不正确")
    private Integer openStyle;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)")
    private String authority;

    @Schema(description = "排序")
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    @Schema(description = "上级菜单名称")
    private String parentName;

    @Schema(description = "备注")
    private String mark;

    public @NotBlank(message = "菜单名称不能为空") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "菜单名称不能为空") String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public @Min(value = 0, message = "排序值不能小于0") Integer getSort() {
        return sort;
    }

    public void setSort(@Min(value = 0, message = "排序值不能小于0") Integer sort) {
        this.sort = sort;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
