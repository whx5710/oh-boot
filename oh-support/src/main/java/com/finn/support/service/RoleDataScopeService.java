package com.finn.support.service;

import java.util.List;

/**
 * 角色数据权限
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface RoleDataScopeService {

    /**
     * 保存或修改
     * @param roleId      角色ID
     * @param deptIdList   部门ID列表
     */
    void saveOrUpdate(Long roleId, List<Long> deptIdList);

    /**
     * 根据角色ID，获取部门ID列表
     */
    List<Long> getDeptIdList(Long roleId);

    /**
     * 根据角色id列表，删除角色数据权限关系
     * @param roleIdList 角色id列表
     */
    void deleteByRoleIdList(List<Long> roleIdList);
}