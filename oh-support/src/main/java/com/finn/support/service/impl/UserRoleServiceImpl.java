package com.finn.support.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.mapper.UserRoleMapper;
import com.finn.support.service.UserRoleService;
import com.finn.support.entity.UserRoleEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserRoleMapper userRoleMapper){
        this.userRoleMapper = userRoleMapper;
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
            UserDetail user = SecurityUser.getUser();
            List<UserRoleEntity> roleList = insertRoleIdList.stream().map(roleId -> {
                UserRoleEntity entity = new UserRoleEntity();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                entity.setCreator(SecurityUser.getUserId());
                entity.setTenantId(user==null?null:user.getTenantId());
                entity.setCreateTime(LocalDateTime.now());
                return entity;
            }).collect(Collectors.toList());

            // 批量新增
            userRoleMapper.saveBatch(roleList);
        }

        // 需要删除的角色ID
        Collection<Long> deleteRoleIdList = CollUtil.subtract(dbRoleIdList, roleIdList);
        if (CollUtil.isNotEmpty(deleteRoleIdList)){
            UserRoleEntity param = new UserRoleEntity();
            param.setUserId(userId);
            param.setUpdater(SecurityUser.getUserId());
            userRoleMapper.deleteByRoleIdList((List<Long>) deleteRoleIdList, param);
        }
    }

    @Override
    public void saveUserList(Long roleId, List<Long> userIdList) {
        List<UserRoleEntity> list = userIdList.stream().map(userId -> {
            UserRoleEntity entity = new UserRoleEntity();
            entity.setUserId(userId);
            entity.setRoleId(roleId);
            entity.setCreator(SecurityUser.getUserId());
            entity.setCreateTime(LocalDateTime.now());
            return entity;
        }).collect(Collectors.toList());

        // 批量新增
        userRoleMapper.saveBatch(list);
    }

    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        UserRoleEntity param = new UserRoleEntity();
        param.setUpdater(SecurityUser.getUserId());
        param.setUpdateTime(LocalDateTime.now());
        userRoleMapper.deleteByRoleIdList(roleIdList, param);
    }

    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        userRoleMapper.deleteByUserIdList(userIdList, null);
    }

    @Override
    public void deleteByUserIdList(Long roleId, List<Long> userIdList) {
        UserRoleEntity param = new UserRoleEntity();
        param.setRoleId(roleId);
        param.setUpdateTime(LocalDateTime.now());
        param.setUpdater(SecurityUser.getUserId());
        userRoleMapper.deleteByUserIdList(userIdList, param);
    }

    @Override
    public List<Long> getRoleIdList(Long userId) {
        return userRoleMapper.getRoleIdList(userId);
    }
}