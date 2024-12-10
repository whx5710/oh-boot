package com.iris.support.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.iris.support.mapper.SysUserRoleMapper;
import com.iris.support.service.SysUserRoleService;
import com.iris.support.entity.SysUserRoleEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关系
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;

    public SysUserRoleServiceImpl(SysUserRoleMapper sysUserRoleMapper){
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        // 数据库角色ID列表
        List<Long> dbRoleIdList = getRoleIdList(userId);
        if(roleIdList == null){
            roleIdList = new ArrayList<>();
        }
        // 需要新增的角色ID
        Collection<Long> insertRoleIdList = CollUtil.subtract(roleIdList, dbRoleIdList);
        if (CollUtil.isNotEmpty(insertRoleIdList)){
            List<SysUserRoleEntity> roleList = insertRoleIdList.stream().map(roleId -> {
                SysUserRoleEntity entity = new SysUserRoleEntity();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                return entity;
            }).collect(Collectors.toList());

            // 批量新增
            sysUserRoleMapper.saveBatch(roleList);
        }

        // 需要删除的角色ID
        Collection<Long> deleteRoleIdList = CollUtil.subtract(dbRoleIdList, roleIdList);
        if (CollUtil.isNotEmpty(deleteRoleIdList)){
            SysUserRoleEntity param = new SysUserRoleEntity();
            param.setUserId(userId);
            sysUserRoleMapper.deleteByRoleIdList((List<Long>) deleteRoleIdList, param);
        }
    }

    @Override
    public void saveUserList(Long roleId, List<Long> userIdList) {
        List<SysUserRoleEntity> list = userIdList.stream().map(userId -> {
            SysUserRoleEntity entity = new SysUserRoleEntity();
            entity.setUserId(userId);
            entity.setRoleId(roleId);
            return entity;
        }).collect(Collectors.toList());

        // 批量新增
        sysUserRoleMapper.saveBatch(list);
    }

    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        sysUserRoleMapper.deleteByRoleIdList(roleIdList, new SysUserRoleEntity());
    }

    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        sysUserRoleMapper.deleteByUserIdList(userIdList, null);
    }

    @Override
    public void deleteByUserIdList(Long roleId, List<Long> userIdList) {
        SysUserRoleEntity param = new SysUserRoleEntity();
        param.setRoleId(roleId);
        sysUserRoleMapper.deleteByUserIdList(userIdList, param);
    }

    @Override
    public List<Long> getRoleIdList(Long userId) {
        return sysUserRoleMapper.getRoleIdList(userId);
    }
}