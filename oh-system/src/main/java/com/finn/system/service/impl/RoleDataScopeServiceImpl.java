package com.finn.system.service.impl;

import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.system.entity.RoleDataScopeEntity;
import com.finn.system.mapper.RoleDataScopeMapper;
import com.finn.system.service.RoleDataScopeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色数据权限
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class RoleDataScopeServiceImpl implements RoleDataScopeService {
    private final RoleDataScopeMapper roleDataScopeMapper;

    public RoleDataScopeServiceImpl(RoleDataScopeMapper roleDataScopeMapper){
        this.roleDataScopeMapper = roleDataScopeMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        // 数据库部门ID列表
        List<Long> dbDeptIdList = getDeptIdList(roleId);

        // 需要新增的部门ID
        // Collection<Long> insertDeptIdList = CollUtil.subtract(deptIdList, dbDeptIdList);
        Collection<Long> insertDeptIdList = deptIdList.stream()
                .filter(element -> !dbDeptIdList.contains(element))
                .toList();

        if (!insertDeptIdList.isEmpty()){
            UserDetail user = SecurityUser.getUser();
            String tenantId;
            if(user != null){
                tenantId = user.getTenantId();
            } else {
                tenantId = null;
            }
            List<RoleDataScopeEntity> orgList = insertDeptIdList.stream().map(deptId -> {
                RoleDataScopeEntity entity = new RoleDataScopeEntity();
                entity.setDeptId(deptId);
                entity.setRoleId(roleId);
                entity.setCreateTime(LocalDateTime.now());
                entity.setCreator(SecurityUser.getUserId());
                entity.setTenantId(tenantId);
                return entity;
            }).collect(Collectors.toList());

            // 批量新增
            roleDataScopeMapper.insertBatch(orgList);
        }

        // 需要删除的部门ID
        // Collection<Long> deleteDeptIdList = CollUtil.subtract(dbDeptIdList, deptIdList);
        Collection<Long> deleteDeptIdList = dbDeptIdList.stream()
                .filter(element -> !deptIdList.contains(element))
                .collect(Collectors.toList());
        if (!deleteDeptIdList.isEmpty()){
            UpdateWrapper<RoleDataScopeEntity> updateWrapper = UpdateWrapper.of(RoleDataScopeEntity.class)
                    .set(RoleDataScopeEntity::getDbStatus, 0)
                    .in(RoleDataScopeEntity::getDeptId, (List<Long>) deleteDeptIdList)
                    .eq(RoleDataScopeEntity::getRoleId, roleId);
            roleDataScopeMapper.updateByWrapper(updateWrapper);
        }
    }

    @Override
    public List<Long> getDeptIdList(Long roleId) {
        return roleDataScopeMapper.getDeptIdList(roleId);
    }

    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        UpdateWrapper<RoleDataScopeEntity> updateWrapper = UpdateWrapper.of(RoleDataScopeEntity.class)
                        .set(RoleDataScopeEntity::getDbStatus, 0)
                                .in(RoleDataScopeEntity::getRoleId, roleIdList);
        roleDataScopeMapper.updateByWrapper(updateWrapper);
    }
}