package com.finn.framework.common.constant;

import com.finn.core.cache.RedisKeys;

/**
 * 常量
 *
 * @author 王小费 whx5710@qq.com
 * @since 2025-01-18
 */
public interface CommConstant {

    /**
     * 租户
     */
    String TENANT = "tenant:";

    /**
     * 租户成员key
     */
    String TENANT_PREFIX = RedisKeys.PREFIX + TENANT + "member:";
}
