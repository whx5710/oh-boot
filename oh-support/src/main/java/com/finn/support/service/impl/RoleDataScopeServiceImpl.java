package com.finn.support.service.impl;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.mapper.RoleDataScopeMapper;
import com.finn.support.service.RoleDataScopeService;
import com.finn.support.entity.RoleDataScopeEntity;
import org.springframework.stereotype.Service;

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
@Ds(Constant.DYNAMIC_SYS_DB)
public class RoleDataScopeServiceImpl implements RoleDataScopeService {
    private final RoleDataScopeMapper roleDataScopeMapper;

    public RoleDataScopeServiceImpl(RoleDataScopeMapper roleDataScopeMapper){
        this.roleDataScopeMapper = roleDataScopeMapper;
    }

    @Override
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        // 数据库部门ID列表
        List<Long> dbDeptIdList = getDeptIdList(roleId);

        // 需要新增的部门ID
        // Collection<Long> insertDeptIdList = CollUtil.subtract(deptIdList, dbDeptIdList);
        Collection<Long> insertDeptIdList = deptIdList.stream()
                .filter(element -> !dbDeptIdList.contains(element))
                .collect(Collectors.toList());

        if (insertDeptIdList != null && insertDeptIdList.size() > 0){
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
            roleDataScopeMapper.saveBatch(orgList);
        }

        // 需要删除的部门ID
        // Collection<Long> deleteDeptIdList = CollUtil.subtract(dbDeptIdList, deptIdList);
        Collection<Long> deleteDeptIdList = dbDeptIdList.stream()
                .filter(element -> !deptIdList.contains(element))
                .collect(Collectors.toList());
        if (deleteDeptIdList != null && deleteDeptIdList.size() > 0){
            RoleDataScopeEntity param = new RoleDataScopeEntity();
            param.setRoleId(roleId);
            param.setUpdateTime(LocalDateTime.now());
            param.setUpdater(SecurityUser.getUserId());
            roleDataScopeMapper.deleteDeptIdList((List<Long>) deleteDeptIdList, param);
        }
    }

    @Override
    public List<Long> getDeptIdList(Long roleId) {
        return roleDataScopeMapper.getDeptIdList(roleId);
    }

    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        RoleDataScopeEntity param = new RoleDataScopeEntity();
        param.setUpdater(SecurityUser.getUserId());
        roleDataScopeMapper.deleteByRoleIdList(roleIdList, param);
    }
}