package com.finn.system.service.impl;

import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.datasource.wrapper.Wrapper;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.system.entity.UserRoleEntity;
import com.finn.system.enums.SuperAdminEnum;
import com.finn.system.mapper.UserRoleMapper;
import com.finn.system.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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
        // Collection<Long> insertRoleIdList = CollUtil.subtract(roleIdList, dbRoleIdList);
        Collection<Long> insertRoleIdList = roleIdList.stream()
                .filter(element -> !dbRoleIdList.contains(element))
                .toList();

        if (!insertRoleIdList.isEmpty()){
            UserDetail user = SecurityUser.getUser();
            List<UserRoleEntity> roleList = insertRoleIdList.stream().map(roleId -> {
                UserRoleEntity entity = new UserRoleEntity();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                entity.setCreator(SecurityUser.getUserId());
                entity.setCreateTime(LocalDateTime.now());
                return entity;
            }).collect(Collectors.toList());

            // 批量新增
            userRoleMapper.insertBatch(roleList);
        }

        // 需要删除的角色ID
        List<Long> finalRoleIdList = roleIdList;
        List<Long> deleteRoleIdList = dbRoleIdList.stream()
                .filter(element -> !finalRoleIdList.contains(element))
                .collect(Collectors.toList());
        if (!deleteRoleIdList.isEmpty()){
            Wrapper<UserRoleEntity> updateWrapper = UpdateWrapper.of(UserRoleEntity.class)
                    .set(UserRoleEntity::getDbStatus, 0).in(UserRoleEntity::getRoleId, deleteRoleIdList)
                    .eq(UserRoleEntity::getUserId, userId);
            userRoleMapper.updateByWrapper(updateWrapper);
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
        userRoleMapper.insertBatch(list);
    }

    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        Wrapper<UserRoleEntity> updateWrapper = UpdateWrapper.of(UserRoleEntity.class)
                        .set(UserRoleEntity::getDbStatus, 0).in(UserRoleEntity::getRoleId, roleIdList);
        userRoleMapper.updateByWrapper(updateWrapper);
    }

    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        Wrapper<UserRoleEntity> updateWrapper = UpdateWrapper.of(UserRoleEntity.class)
                        .set(UserRoleEntity::getDbStatus, 0)
                                .in(UserRoleEntity::getUserId, userIdList);
        userRoleMapper.updateByWrapper(updateWrapper);
    }

    @Override
    public void deleteByUserIdList(Long roleId, List<Long> userIdList) {
        Wrapper<UserRoleEntity> updateWrapper = UpdateWrapper.of(UserRoleEntity.class)
                .set(UserRoleEntity::getDbStatus, 0)
                    .in(UserRoleEntity::getUserId, userIdList)
                    .eq(UserRoleEntity::getRoleId, roleId);
        userRoleMapper.updateByWrapper(updateWrapper);
    }

    @Override
    public List<Long> getRoleIdList(Long userId) {
        return userRoleMapper.getRoleIdList(userId);
    }

    @Override
    public Set<String> getUserAuthority(UserDetail user) {
        // 系统管理员，拥有最高权限
        List<String> authorityList = new ArrayList<>();
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            authorityList = userRoleMapper.getAuthorityList();
        } else {
            if(user.getRoleIds() != null && !user.getRoleIds().isEmpty()){
                authorityList = userRoleMapper.getUserAuthorityList(user.getRoleIds());
            }
        }

        // 用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String authority : authorityList) {
            if (authority == null || authority.isEmpty()) {
                continue;
            }
            permsSet.addAll(Arrays.asList(authority.trim().split(",")));
        }
        return permsSet;
    }
}