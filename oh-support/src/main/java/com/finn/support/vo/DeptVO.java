package com.finn.support.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.utils.TreeNode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.finn.core.utils.DateUtils;

import java.time.LocalDateTime;

/**
 * 部门列表
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class DeptVO extends TreeNode<DeptVO> {

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String name;

    /**
     * 排序
     */
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 租户
     */
    private String tenantName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    /**
     * 上级名称
     */
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}