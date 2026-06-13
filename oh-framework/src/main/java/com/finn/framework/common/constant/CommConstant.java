package com.finn.framework.common.constant;

import com.finn.framework.cache.RedisKeys;

import static com.finn.framework.cache.RedisKeys.PREFIX;

/**
 * 常量
 *
 * @author 王小费 whx5710@qq.com
 * @since 2025-01-18
 */
public interface CommConstant {

    String ROLE = "role:";
    /**
     * 角色编码
     */
    String ROLE__PREFIX = RedisKeys.PREFIX + ROLE + "code:";

    /**
     * 错误日志前缀
     */
    String ERROR_LOG_KEY = PREFIX + "error:log";
}
