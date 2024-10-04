package com.iris.sys.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.iris.framework.common.utils.DateUtils;
import com.iris.framework.common.utils.TreeNode;

import java.time.LocalDateTime;

/**
 * 机构列表
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Schema(description = "机构")
public class SysOrgVO extends TreeNode<SysOrgVO> {

    @Schema(description = "机构名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "机构名称不能为空")
    private String name;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    @Schema(description = "上级名称")
    private String parentName;

    // 备注
    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
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

    public String getNote() {
        return note;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public void setNote(String note) {
        this.note = note;
    }
}