package com.finn.sys.base.mapper;

import com.finn.framework.datasource.service.ProviderService;
import com.finn.sys.base.entity.TenantMemberEntity;
import com.finn.sys.base.query.TenantMemberQuery;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * 租户信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-01-18
 */
@Mapper
public interface TenantMemberMapper {

    List<TenantMemberEntity> tenantList(TenantMemberQuery query);


    @InsertProvider(method = ProviderService.INSERT, type = ProviderService.class)
    void save(TenantMemberEntity entity);

    @UpdateProvider(method = ProviderService.UPDATE, type = ProviderService.class)
    void update(TenantMemberEntity entity);

    TenantMemberEntity getById(@Param("id")Long id);
}
