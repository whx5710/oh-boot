package com.finn.system.service.impl;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.PageResult;
import com.finn.framework.datasource.utils.QueryWrapper;
import com.finn.framework.datasource.utils.UpdateWrapper;
import com.finn.framework.datasource.utils.Wrapper;
import com.finn.system.cache.TenantCache;
import com.finn.system.convert.PostConvert;
import com.finn.system.entity.PostEntity;
import com.finn.system.mapper.PostMapper;
import com.finn.system.query.PostQuery;
import com.finn.system.service.PostService;
import com.finn.system.service.UserPostService;
import com.finn.system.vo.PostVO;
import com.github.pagehelper.Page;
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
        Page<PostEntity> page = postMapper.selectPageByWrapper(getQueryWrapper(query));
        List<PostVO> list = PostConvert.INSTANCE.convertList(page.getResult());
        for(PostVO vo: list){
            vo.setTenantName(tenantCache.getNameByTenantId(vo.getTenantId()));
        }
        return new PageResult<>(list, page.getTotal());
    }

    @Override
    public List<PostVO> getList() {
        List<PostEntity> entityList = postMapper.selectListByWrapper(QueryWrapper.of(PostEntity.class)
                .eq(PostEntity::getDbStatus, 1).orderBy("sort"));
        return PostConvert.INSTANCE.convertList(entityList);
    }

    @Override
    public void save(PostVO vo) {
        PostEntity entity = PostConvert.INSTANCE.convert(vo);
        AssertUtils.isBlank(entity.getPostCode(), "岗位编码");
        List<PostEntity> list = postMapper.selectListByWrapper(QueryWrapper.of(PostEntity.class)
                .eq(PostEntity::getDbStatus, 1).eq(PostEntity::getPostCode, entity.getPostCode()));
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
            List<PostEntity> list = postMapper.selectListByWrapper(QueryWrapper.of(PostEntity.class)
                    .eq(PostEntity::getDbStatus, 1).eq(PostEntity::getPostCode, postCode));
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
        postMapper.updateByWrapper(UpdateWrapper.of(PostEntity.class).set(PostEntity::getDbStatus, 0)
                .in(PostEntity::getId, idList));
        // 删除岗位用户关系
        userPostService.deleteByPostIdList(idList);
    }

    @Override
    public PostEntity getById(Long id) {
        return postMapper.getById(id);
    }

    /**
     * 构建查询条件
     * @param query
     * @return
     */
    private Wrapper<PostEntity> getQueryWrapper(PostQuery query){
        if(query == null){
            return QueryWrapper.of(PostEntity.class).eq(PostEntity::getDbStatus, 1).orderBy("sort");
        }else{
            return QueryWrapper.of(PostEntity.class).eq(PostEntity::getDbStatus, 1)
                    .like(PostEntity::getPostName, query.getPostName())
                    .eq(PostEntity::getPostCode, query.getPostCode())
                    .eq(PostEntity::getTenantId, query.getTenantId())
                    .eq(PostEntity::getStatus, query.getStatus())
                    .orderBy("sort").page(query.getPageNum(), query.getPageSize());
        }
    }
}