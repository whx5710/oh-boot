package com.iris.support.cache;

import com.iris.core.cache.RedisCache;
import com.iris.core.utils.JsonUtils;
import com.iris.framework.common.constant.CommConstant;
import org.springframework.stereotype.Service;

import java.util.Map;

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
        Map map = getTenantToMap(tenantId);
        if(map != null && map.containsKey("tenantName") && map.get("tenantName") != null){
            return (String) map.get("tenantName");
        }else{
            return null;
        }
    }

    /**
     * 获取租户基本信息
     * @param tenantId 租户ID
     * @return map
     */
    public Map getTenantToMap(String tenantId){
        if(tenantId == null || tenantId.isEmpty()){
            return null;
        }
        Object obj = redisCache.get(CommConstant.TENANT_PREFIX + tenantId);
        if(obj != null){
            return JsonUtils.convertValue(obj, Map.class);
        }else{
            return null;
        }
    }
}
