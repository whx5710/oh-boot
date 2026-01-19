package com.finn.system.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.framework.datasource.service.ModifyProviderService;
import com.finn.system.entity.TenantMemberEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 租户信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-01-18
 */
@Mapper
public interface TenantMemberMapper extends BaseMapper<TenantMemberEntity> {

    @InsertProvider(method = ModifyProviderService.INSERT, type = ModifyProviderService.class)
    void save(TenantMemberEntity entity);

    @UpdateProvider(method = ModifyProviderService.UPDATE, type = ModifyProviderService.class)
    void update(TenantMemberEntity entity);

}
