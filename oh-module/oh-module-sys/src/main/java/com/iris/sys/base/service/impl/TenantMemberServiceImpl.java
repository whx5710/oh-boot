package com.iris.sys.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.core.cache.RedisCache;
import com.iris.core.utils.AssertUtils;
import com.iris.core.utils.PageResult;
import com.iris.framework.common.constant.CommConstant;
import com.iris.sys.base.convert.TenantMemberConvert;
import com.iris.sys.base.entity.TenantMemberEntity;
import com.iris.sys.base.mapper.TenantMemberMapper;
import com.iris.sys.base.query.TenantMemberQuery;
import com.iris.sys.base.service.TenantMemberService;
import com.iris.sys.base.vo.TenantMemberVO;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 租户信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-01-18
 */
@Service
public class TenantMemberServiceImpl implements TenantMemberService {

    private final TenantMemberMapper tenantMemberMapper;

    private final RedisCache redisCache;

    public TenantMemberServiceImpl(TenantMemberMapper tenantMemberMapper, RedisCache redisCache){
        this.tenantMemberMapper = tenantMemberMapper;
        this.redisCache = redisCache;
    }

    @Override
    public PageResult<TenantMemberVO> page(TenantMemberQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TenantMemberEntity> list = tenantMemberMapper.tenantList(query);
        PageInfo<TenantMemberEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(TenantMemberConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void save(TenantMemberVO vo) {
        AssertUtils.isBlank(vo.getTenantId(), "租户ID");
        TenantMemberEntity entity = TenantMemberConvert.INSTANCE.convert(vo);
        tenantMemberMapper.save(entity);
        redisCache.set(CommConstant.TENANT_PREFIX + entity.getTenantId(), entity.toDto());
    }

    @Override
    public void update(TenantMemberVO vo) {
        tenantMemberMapper.update(TenantMemberConvert.INSTANCE.convert(vo));
        TenantMemberEntity entity = tenantMemberMapper.getById(vo.getId());
        redisCache.set(CommConstant.TENANT_PREFIX + entity.getTenantId(), entity.toDto());
    }

    @Override
    public TenantMemberEntity getById(Long id) {
        return tenantMemberMapper.getById(id);
    }

    /**
     * 初始化
     */
    @PostConstruct
    private void init(){
        List<TenantMemberEntity> list = tenantMemberMapper.tenantList(new TenantMemberQuery());
        redisCache.deleteAll(CommConstant.TENANT_PREFIX + "*");
        if(list != null){
            for(TenantMemberEntity item : list){
                // 以json格式缓存到redis，方便直接读取
                redisCache.set(CommConstant.TENANT_PREFIX + item.getTenantId(), item.toDto());
            }
        }
    }
}
