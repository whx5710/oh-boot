package com.finn.framework.security.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * 获取当前用户<br/>
 * AuthenticationFilter 中将用户缓存起来 (SecurityContextHolder.setContext)
 * @author 王小费 whx5710@qq.com
 *
 */
public class SecurityUser {

    private static final Logger log = LoggerFactory.getLogger(SecurityUser.class);
    /**
     * 获取用户信息
     */
    public static UserDetail getUser() {
        UserDetail user;
        try {
            user = (UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            // log.warn("未获取到用户信息！{}", e.getMessage());
            return null;
        }
        return user;
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        UserDetail user = getUser();
        if(user == null){
            return null;
        }
        return user.getId();
    }

    /**
     * 判断是否为租户
     * @return b
     */
    public static Boolean isTenant(){
        UserDetail user = getUser();
        if(user == null){
            log.warn("未获取到用户信息，是否租户默认true");
            return true;
        }
        return user.getTenantId() != null && !user.getTenantId().isEmpty();
    }

}