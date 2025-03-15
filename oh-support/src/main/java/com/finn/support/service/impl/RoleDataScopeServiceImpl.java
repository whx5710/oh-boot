package com.finn.support.service.impl;

import cn.hutool.core.collection.CollUtil;
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
public class RoleDataScopeServiceImpl implements RoleDataScopeService {
    private final RoleDataScopeMapper roleDataScopeMapper;

    public RoleDataScopeServiceImpl(RoleDataScopeMapper roleDataScopeMapper){
        this.roleDataScopeMapper = roleDataScopeMapper;
    }

    @Override
    public void saveOrUpdate(Long roleId, List<Long> orgIdList) {
        // 数据库机构ID列表
        List<Long> dbOrgIdList = getOrgIdList(roleId);

        // 需要新增的机构ID
        Collection<Long> insertOrgIdList = CollUtil.subtract(orgIdList, dbOrgIdList);
        if (CollUtil.isNotEmpty(insertOrgIdList)){
            UserDetail user = SecurityUser.getUser();
            String tenantId;
            if(user != null){
                tenantId = user.getTenantId();
            } else {
                tenantId = null;
            }
            List<RoleDataScopeEntity> orgList = insertOrgIdList.stream().map(orgId -> {
                RoleDataScopeEntity entity = new RoleDataScopeEntity();
                entity.setOrgId(orgId);
                entity.setRoleId(roleId);
                entity.setCreateTime(LocalDateTime.now());
                entity.setCreator(SecurityUser.getUserId());
                entity.setTenantId(tenantId);
                return entity;
            }).collect(Collectors.toList());

            // 批量新增
            roleDataScopeMapper.saveBatch(orgList);
        }

        // 需要删除的机构ID
        Collection<Long> deleteOrgIdList = CollUtil.subtract(dbOrgIdList, orgIdList);
        if (CollUtil.isNotEmpty(deleteOrgIdList)){
            RoleDataScopeEntity param = new RoleDataScopeEntity();
            param.setRoleId(roleId);
            param.setUpdateTime(LocalDateTime.now());
            param.setUpdater(SecurityUser.getUserId());
            roleDataScopeMapper.deleteOrgIdList((List<Long>) deleteOrgIdList, param);
        }
    }

    @Override
    public List<Long> getOrgIdList(Long roleId) {
        return roleDataScopeMapper.getOrgIdList(roleId);
    }

    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        RoleDataScopeEntity param = new RoleDataScopeEntity();
        param.setUpdater(SecurityUser.getUserId());
        roleDataScopeMapper.deleteByRoleIdList(roleIdList, param);
    }
}