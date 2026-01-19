package com.finn.system.service.impl;

import com.finn.core.exception.ServerException;
import com.finn.framework.datasource.utils.Wrapper;
import com.finn.framework.datasource.utils.QueryWrapper;
import com.finn.system.entity.DeptEntity;
import com.finn.system.service.DeptService;
import com.github.pagehelper.Page;
import com.finn.core.cache.RedisCache;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.PageResult;
import com.finn.framework.common.constant.CommConstant;
import com.finn.system.convert.TenantMemberConvert;
import com.finn.system.entity.TenantMemberEntity;
import com.finn.system.mapper.TenantMemberMapper;
import com.finn.system.query.TenantMemberQuery;
import com.finn.system.service.TenantMemberService;
import com.finn.system.vo.TenantMemberVO;
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

    private final DeptService deptService;

    public TenantMemberServiceImpl(TenantMemberMapper tenantMemberMapper, RedisCache redisCache,
                                   DeptService deptService){
        this.tenantMemberMapper = tenantMemberMapper;
        this.redisCache = redisCache;
        this.deptService = deptService;
    }

    @Override
    public PageResult<TenantMemberVO> page(TenantMemberQuery query) {
        Page<TenantMemberEntity> page = tenantMemberMapper.selectPageByWrapper(getQueryWrapper(query));
        return new PageResult<>(TenantMemberConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
    }

    @Override
    public void save(TenantMemberVO vo) {
        AssertUtils.isBlank(vo.getTenantId(), "租户ID");
        AssertUtils.isNull(vo.getDeptId(), "根部门");
        TenantMemberEntity entity = TenantMemberConvert.INSTANCE.convert(vo);
        // 判断租户ID是否存在
        Wrapper<TenantMemberEntity> params = QueryWrapper.of(TenantMemberEntity.class)
                .eq(TenantMemberEntity::getTenantId, entity.getTenantId()).eq(TenantMemberEntity::getDbStatus, 1);
        List<TenantMemberEntity> list = tenantMemberMapper.selectListByWrapper(params);
        if(list != null && !list.isEmpty()){
            throw new ServerException("租户ID已存在！[" + list.getFirst().getTenantName()+ "]");
        }
        // 判断根部门是否被其他租户绑定
        DeptEntity deptEntity = deptService.getById(vo.getDeptId());
        if(deptEntity == null || deptEntity.getId() == null){
            throw new ServerException("部门不存在");
        }
        if(deptEntity.getTenantId() != null && !deptEntity.getTenantId().isEmpty()){
            throw new ServerException("该部门已绑定其他租户，不能重复绑定！【" + deptEntity.getTenantId() + "】");
        }
        deptEntity.setTenantId(vo.getTenantId());
        tenantMemberMapper.save(entity);
        redisCache.set(CommConstant.TENANT_PREFIX + entity.getTenantId(), entity.toDto());
        deptService.updateById(deptEntity);
    }

    @Override
    public void update(TenantMemberVO vo) {
        tenantMemberMapper.update(TenantMemberConvert.INSTANCE.convert(vo));
        TenantMemberEntity entity = tenantMemberMapper.findById(vo.getId(), TenantMemberEntity.class);
        redisCache.set(CommConstant.TENANT_PREFIX + entity.getTenantId(), entity.toDto());
    }

    @Override
    public TenantMemberEntity getById(Long id) {
        return tenantMemberMapper.findById(id, TenantMemberEntity.class);
    }

    @Override
    public void delete(Long id) {
        TenantMemberEntity entity = tenantMemberMapper.findById(id, TenantMemberEntity.class);
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
     * 构建查询条件
     * @param query
     * @return
     */
    private Wrapper<TenantMemberEntity> getQueryWrapper(TenantMemberQuery query){
        return QueryWrapper.of(TenantMemberEntity.class).eq(TenantMemberEntity::getDbStatus, 1)
                .like(TenantMemberEntity::getTenantName, query.getTenantName())
                .eq(TenantMemberEntity::getStatus, query.getStatus())
                .jointSQL("(tenant_name like concat('%',#{keyWord}, '%') " +
                        "or tenant_id like concat('%',#{keyWord},'%') " +
                        "or note like concat('%',#{keyWord}, '%'))","keyWord", query.getKeyWord())
                .page(query.getPageNum(), query.getPageSize())
                .orderBy("sort");
    }

    /**
     * 初始化
     */
    @PostConstruct
    private void init(){
        List<TenantMemberEntity> list = tenantMemberMapper.selectListByWrapper(QueryWrapper.of(TenantMemberEntity.class)
                .eq(TenantMemberEntity::getDbStatus, 1).orderBy("sort"));
        redisCache.deleteAll(CommConstant.TENANT_PREFIX + "*");
        if(list != null){
            for(TenantMemberEntity item : list){
                // 以json格式缓存到redis，方便直接读取
                redisCache.set(CommConstant.TENANT_PREFIX + item.getTenantId(), item.toDto());
            }
        }
    }
}
