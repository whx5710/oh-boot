package com.finn.system.cache;

import com.finn.core.cache.RedisCache;
import com.finn.framework.common.constant.CommConstant;
import com.finn.system.entity.TenantMemberEntity;
import org.springframework.stereotype.Service;

/**
 * 租户信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 2025-01-19
 */
@Service
public class TenantCache {

    private final RedisCache redisCache;

    public TenantCache(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    /**
     * 根据租户ID获取名称
     * @param tenantId 租户ID
     * @return 租户名
     */
    public String getNameByTenantId(String tenantId){
        TenantMemberEntity map = getTenant(tenantId);
        if(map != null && map.getTenantName() != null){
            return map.getTenantName();
        }else{
            return null;
        }
    }

    /**
     * 租户是否有效
     * @param tenantId 租户ID
     * @return b
     */
    public Boolean valid(String tenantId){
        TenantMemberEntity map = getTenant(tenantId);
        if(map == null){
            return false;
        }
        int status = map.getStatus();
        int dbStatus = map.getDbStatus();
        return status == 1 && dbStatus == 1;
    }

    /**
     * 获取租户基本信息
     * @param tenantId 租户ID
     * @return map
     */
    public TenantMemberEntity getTenant(String tenantId){
        if(tenantId == null || tenantId.isEmpty()){
            return null;
        }
        Object obj = redisCache.get(CommConstant.TENANT_PREFIX + tenantId);
        if(obj != null){
            return (TenantMemberEntity) obj;
        }else{
            return null;
        }
    }
}
