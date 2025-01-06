package com.iris.support.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.iris.framework.security.user.SecurityUser;
import com.iris.support.mapper.SysUserPostMapper;
import com.iris.support.service.SysUserPostService;
import com.iris.support.entity.SysUserPostEntity;
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
public class SysUserPostServiceImpl implements SysUserPostService {

    private final SysUserPostMapper sysUserPostMapper;

    public SysUserPostServiceImpl(SysUserPostMapper sysUserPostMapper){
        this.sysUserPostMapper = sysUserPostMapper;
    }

    @Override
    public void saveOrUpdate(Long userId, List<Long> postIdList) {
        // 数据库岗位ID列表
        List<Long> dbPostIdList = getPostIdList(userId);
        if(postIdList == null){
            postIdList = new ArrayList<>();
        }
        // 需要新增的岗位ID
        Collection<Long> insertPostIdList = CollUtil.subtract(postIdList, dbPostIdList);
        if (CollUtil.isNotEmpty(insertPostIdList)){
            List<SysUserPostEntity> postList = insertPostIdList.stream().map(postId -> {
                SysUserPostEntity entity = new SysUserPostEntity();
                entity.setUserId(userId);
                entity.setPostId(postId);
                entity.setCreator(SecurityUser.getUserId());
                entity.setCreateTime(LocalDateTime.now());
                return entity;
            }).collect(Collectors.toList());
            // 批量新增
            sysUserPostMapper.saveBatch(postList);
        }

        // 需要删除的岗位ID
        Collection<Long> deletePostIdList = CollUtil.subtract(dbPostIdList, postIdList);
        if (CollUtil.isNotEmpty(deletePostIdList)){
            SysUserPostEntity param = new SysUserPostEntity();
            param.setUserId(userId);
            param.setUpdater(SecurityUser.getUserId());
            sysUserPostMapper.deleteByPostIdList((List<Long>) deletePostIdList, param);
        }
    }

    @Override
    public void deleteByPostIdList(List<Long> postIdList) {
        SysUserPostEntity param = new SysUserPostEntity();
        param.setUpdater(SecurityUser.getUserId());
        sysUserPostMapper.deleteByPostIdList(postIdList, param);
    }

    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        SysUserPostEntity param = new SysUserPostEntity();
        param.setUpdater(SecurityUser.getUserId());
        sysUserPostMapper.deleteByUserIdList(userIdList, param);
    }

    @Override
    public List<Long> getPostIdList(Long userId) {
        return sysUserPostMapper.getPostIdList(userId);
    }
}