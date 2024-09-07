package com.iris.system.base.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.iris.system.base.mapper.SysUserPostMapper;
import com.iris.system.base.service.SysUserPostService;
import com.iris.system.base.entity.SysUserPostEntity;
import org.springframework.stereotype.Service;

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
            sysUserPostMapper.deleteByPostIdList((List<Long>) deletePostIdList, param);
        }
    }

    @Override
    public void deleteByPostIdList(List<Long> postIdList) {
        sysUserPostMapper.deleteByPostIdList(postIdList, new SysUserPostEntity());
    }

    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        sysUserPostMapper.deleteByUserIdList(userIdList, new SysUserPostEntity());
    }

    @Override
    public List<Long> getPostIdList(Long userId) {
        return sysUserPostMapper.getPostIdList(userId);
    }
}