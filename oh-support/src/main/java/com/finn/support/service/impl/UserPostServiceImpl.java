package com.finn.support.service.impl;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.framework.security.user.SecurityUser;
import com.finn.support.mapper.UserPostMapper;
import com.finn.support.service.UserPostService;
import com.finn.support.entity.UserPostEntity;
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
@Ds(Constant.DYNAMIC_SYS_DB)
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
                .collect(Collectors.toList());
        if (insertPostIdList != null && insertPostIdList.size() > 0){
            List<UserPostEntity> postList = insertPostIdList.stream().map(postId -> {
                UserPostEntity entity = new UserPostEntity();
                entity.setUserId(userId);
                entity.setPostId(postId);
                entity.setCreator(SecurityUser.getUserId());
                entity.setCreateTime(LocalDateTime.now());
                return entity;
            }).collect(Collectors.toList());
            // 批量新增
            userPostMapper.saveBatch(postList);
        }

        // 需要删除的岗位ID
        // Collection<Long> deletePostIdList = CollUtil.subtract(dbPostIdList, postIdList);
        List<Long> finalPostIdList = postIdList;
        Collection<Long> deletePostIdList = dbPostIdList.stream()
                .filter(element -> !finalPostIdList.contains(element))
                .collect(Collectors.toList());
        if (deletePostIdList != null && deletePostIdList.size() > 0){
            UserPostEntity param = new UserPostEntity();
            param.setUserId(userId);
            param.setUpdater(SecurityUser.getUserId());
            userPostMapper.deleteByPostIdList((List<Long>) deletePostIdList, param);
        }
    }

    @Override
    public void deleteByPostIdList(List<Long> postIdList) {
        UserPostEntity param = new UserPostEntity();
        param.setUpdater(SecurityUser.getUserId());
        userPostMapper.deleteByPostIdList(postIdList, param);
    }

    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        UserPostEntity param = new UserPostEntity();
        param.setUpdater(SecurityUser.getUserId());
        userPostMapper.deleteByUserIdList(userIdList, param);
    }

    @Override
    public List<Long> getPostIdList(Long userId) {
        return userPostMapper.getPostIdList(userId);
    }
}