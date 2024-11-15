package com.iris.framework.security.user;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取当前用户
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class SecurityUser {

    //private static final Logger log = LoggerFactory.getLogger(SecurityUser.class);
    /**
     * 获取用户信息
     */
    public static UserDetail getUser() {
        UserDetail user;
        try {
            user = (UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            // log.warn("未获取到用户信息！{}", e.getMessage());
            return new UserDetail();
        }
        return user;
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        return getUser().getId();
    }

}