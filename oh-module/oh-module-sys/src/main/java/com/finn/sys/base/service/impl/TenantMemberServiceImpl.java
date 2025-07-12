package com.finn.sys.base.service.impl;

import com.finn.core.exception.ServerException;
import com.finn.framework.utils.ParamsBuilder;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.core.cache.RedisCache;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.PageResult;
import com.finn.framework.common.constant.CommConstant;
import com.finn.sys.base.convert.TenantMemberConvert;
import com.finn.sys.base.entity.TenantMemberEntity;
import com.finn.sys.base.mapper.TenantMemberMapper;
import com.finn.sys.base.query.TenantMemberQuery;
import com.finn.sys.base.service.TenantMemberService;
import com.finn.sys.base.vo.TenantMemberVO;
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
        Page<TenantMemberEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TenantMemberEntity> list = tenantMemberMapper.tenantList(query);
        return new PageResult<>(TenantMemberConvert.INSTANCE.convertList(list), page.getTotal());
    }

    @Override
    public void save(TenantMemberVO vo) {
        AssertUtils.isBlank(vo.getTenantId(), "租户ID");
        TenantMemberEntity entity = TenantMemberConvert.INSTANCE.convert(vo);
        // 判断租户ID是否存在
        ParamsBuilder<TenantMemberEntity> params = ParamsBuilder.of(TenantMemberEntity.class)
                .eq(TenantMemberEntity::getTenantId, entity.getTenantId()).eq(TenantMemberEntity::getDbStatus, 1);
        List<TenantMemberEntity> list = tenantMemberMapper.selectListByParam(params);
        if(list != null && !list.isEmpty()){
            throw new ServerException("租户ID已存在！[" + list.getFirst().getTenantName()+ "]");
        }
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

    @Override
    public void delete(Long id) {
        TenantMemberEntity entity = tenantMemberMapper.getById(id);
        if(entity == null || entity.getId() == null){
            throw new ServerException("未找到对应租户信息，删除失败");
        }
        if(entity.getStatus() == 1){
            throw new ServerException("租户为正常状态，不能删除");
        }
        entity.setDbStatus(0);
        tenantMemberMapper.update(entity);
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
