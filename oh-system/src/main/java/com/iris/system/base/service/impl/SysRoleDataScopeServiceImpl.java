package com.iris.system.base.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.iris.system.base.mapper.SysRoleDataScopeMapper;
import com.iris.system.base.service.SysRoleDataScopeService;
import com.iris.system.base.entity.SysRoleDataScopeEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SysRoleDataScopeServiceImpl implements SysRoleDataScopeService {
    private final SysRoleDataScopeMapper sysRoleDataScopeMapper;

    public SysRoleDataScopeServiceImpl(SysRoleDataScopeMapper sysRoleDataScopeMapper){
        this.sysRoleDataScopeMapper = sysRoleDataScopeMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> orgIdList) {
        // 数据库机构ID列表
        List<Long> dbOrgIdList = getOrgIdList(roleId);

        // 需要新增的机构ID
        Collection<Long> insertOrgIdList = CollUtil.subtract(orgIdList, dbOrgIdList);
        if (CollUtil.isNotEmpty(insertOrgIdList)){
            List<SysRoleDataScopeEntity> orgList = insertOrgIdList.stream().map(orgId -> {
                SysRoleDataScopeEntity entity = new SysRoleDataScopeEntity();
                entity.setOrgId(orgId);
                entity.setRoleId(roleId);
                return entity;
            }).collect(Collectors.toList());

            // 批量新增
            sysRoleDataScopeMapper.saveBatch(orgList);
        }

        // 需要删除的机构ID
        Collection<Long> deleteOrgIdList = CollUtil.subtract(dbOrgIdList, orgIdList);
        if (CollUtil.isNotEmpty(deleteOrgIdList)){
            SysRoleDataScopeEntity param = new SysRoleDataScopeEntity();
            param.setRoleId(roleId);
            sysRoleDataScopeMapper.deleteOrgIdList((List<Long>) deleteOrgIdList, param);
        }
    }

    @Override
    public List<Long> getOrgIdList(Long roleId) {
        return sysRoleDataScopeMapper.getOrgIdList(roleId);
    }

    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        sysRoleDataScopeMapper.deleteByRoleIdList(roleIdList, new SysRoleDataScopeEntity());
    }
}