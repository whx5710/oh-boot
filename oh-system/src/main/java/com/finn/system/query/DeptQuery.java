package com.finn.system.query;

import com.finn.framework.query.Query;

import java.util.List;

/**
 * 部门查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class DeptQuery extends Query {
    /**
     * 部门名称
     */
    private String name;

    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 部门ID集合
     */
    private List<Long> deptIds;

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

    public List<Long> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Long> deptIds) {
        this.deptIds = deptIds;
    }

}
