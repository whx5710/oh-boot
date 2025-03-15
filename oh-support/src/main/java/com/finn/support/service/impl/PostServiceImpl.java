package com.finn.support.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.AssertUtils;
import com.finn.support.cache.TenantCache;
import com.finn.support.mapper.PostMapper;
import com.finn.support.query.PostQuery;
import com.finn.support.vo.PostVO;
import com.finn.support.convert.PostConvert;
import com.finn.support.service.UserPostService;
import com.finn.core.utils.PageResult;
import com.finn.support.entity.PostEntity;
import com.finn.support.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 岗位管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class PostServiceImpl implements PostService {
    private final UserPostService userPostService;
    private final PostMapper postMapper;
    private final TenantCache tenantCache;

    public PostServiceImpl(UserPostService userPostService, PostMapper postMapper,
                           TenantCache tenantCache) {
        this.userPostService = userPostService;
        this.postMapper = postMapper;
        this.tenantCache = tenantCache;
    }

    @Override
    public PageResult<PostVO> page(PostQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<PostEntity> entityList = postMapper.getList(query);
        PageInfo<PostEntity> pageInfo = new PageInfo<>(entityList);
        List<PostVO> list = PostConvert.INSTANCE.convertList(pageInfo.getList());
        for(PostVO vo: list){
            vo.setTenantName(tenantCache.getNameByTenantId(vo.getTenantId()));
        }
        return new PageResult<>(list, pageInfo.getTotal());
    }

    @Override
    public List<PostVO> getList() {
        PostQuery query = new PostQuery();
        //正常岗位列表
        query.setStatus(1);
        List<PostEntity> entityList = postMapper.getList(query);

        return PostConvert.INSTANCE.convertList(entityList);
    }

    @Override
    public void save(PostVO vo) {
        PostEntity entity = PostConvert.INSTANCE.convert(vo);
        AssertUtils.isBlank(entity.getPostCode(), "岗位编码");
        PostQuery params = new PostQuery();
        params.setPostCode(entity.getPostCode());
        List<PostEntity> list = postMapper.getList(params);
        if(!ObjectUtils.isEmpty(list)){
            throw new ServerException("岗位编码已存在");
        }
        postMapper.insertPost(entity);
    }

    @Override
    public void update(PostVO vo) {
        PostEntity entity = PostConvert.INSTANCE.convert(vo);
        String postCode = entity.getPostCode();
        if(postCode != null && !postCode.isEmpty()){
            PostQuery params = new PostQuery();
            params.setPostCode(postCode);
            List<PostEntity> list = postMapper.getList(params);
            if(!ObjectUtils.isEmpty(list)){
                for(PostEntity item : list){
                    if(!Objects.equals(item.getId(), entity.getId())){
                        throw new ServerException("岗位编码已存在!");
                    }
                }
            }
        }
        postMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        // 删除岗位
        idList.forEach(id -> {
            PostEntity p = new PostEntity();
            p.setId(id);
            p.setDbStatus(0);
            postMapper.updateById(p);
        });
        // 删除岗位用户关系
        userPostService.deleteByPostIdList(idList);
    }

    @Override
    public PostEntity getById(Long id) {
        return postMapper.getById(id);
    }

}