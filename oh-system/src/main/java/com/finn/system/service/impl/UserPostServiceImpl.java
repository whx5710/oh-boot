package com.finn.system.service.impl;

import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.datasource.wrapper.Wrapper;
import com.finn.framework.security.user.SecurityUser;
import com.finn.system.entity.UserPostEntity;
import com.finn.system.mapper.UserPostMapper;
import com.finn.system.service.UserPostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户岗位关系
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class UserPostServiceImpl implements UserPostService {

    private final UserPostMapper userPostMapper;

    public UserPostServiceImpl(UserPostMapper userPostMapper){
        this.userPostMapper = userPostMapper;
    }

    @Override
    public void saveOrUpdate(Long userId, List<Long> postIdList) {
        // 数据库岗位ID列表
        List<Long> dbPostIdList = getPostIdList(userId);
        if(postIdList == null){
            postIdList = new ArrayList<>();
        }
        // 需要新增的岗位ID
        // Collection<Long> insertPostIdList = CollUtil.subtract(postIdList, dbPostIdList);
        Collection<Long> insertPostIdList = postIdList.stream()
                .filter(element -> !dbPostIdList.contains(element))
                .toList();
        if (!insertPostIdList.isEmpty()){
            List<UserPostEntity> postList = insertPostIdList.stream().map(postId -> {
                UserPostEntity entity = new UserPostEntity();
                entity.setUserId(userId);
                entity.setPostId(postId);
                entity.setCreator(SecurityUser.getUserId());
                entity.setCreateTime(LocalDateTime.now());
                return entity;
            }).collect(Collectors.toList());
            // 批量新增
            userPostMapper.insertBatch(postList);
        }

        // 需要删除的岗位ID
        // Collection<Long> deletePostIdList = CollUtil.subtract(dbPostIdList, postIdList);
        List<Long> finalPostIdList = postIdList;
        Collection<Long> deletePostIdList = dbPostIdList.stream()
                .filter(element -> !finalPostIdList.contains(element))
                .collect(Collectors.toList());
        if (!deletePostIdList.isEmpty()){
            deleteByPostIdList((List<Long>) deletePostIdList);
        }
    }

    @Override
    public void deleteByPostIdList(List<Long> postIdList) {
        Wrapper<UserPostEntity> updateWrapper = UpdateWrapper.of(UserPostEntity.class).set(UserPostEntity::getDbStatus, 0)
                .in(UserPostEntity::getPostId, postIdList);
        userPostMapper.updateByWrapper(updateWrapper);
    }

    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        Wrapper<UserPostEntity> updateWrapper = UpdateWrapper.of(UserPostEntity.class).set(UserPostEntity::getDbStatus, 0)
                .in(UserPostEntity::getUserId, userIdList);
        userPostMapper.updateByWrapper(updateWrapper);
    }

    @Override
    public List<Long> getPostIdList(Long userId) {
        return userPostMapper.getPostIdList(userId);
    }
}