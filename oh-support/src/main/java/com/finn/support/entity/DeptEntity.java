package com.finn.support.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

/**
 * 部门管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_dept")
public class DeptEntity extends BaseEntity {
    /**
     * 上级ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("parent_id")
    private Long parentId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 上级名称
     */
    @TableField(exists = false)
    private String parentName;

    // 备注
    private String note;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}