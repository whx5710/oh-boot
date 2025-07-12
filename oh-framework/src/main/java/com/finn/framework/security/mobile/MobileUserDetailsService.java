package com.finn.framework.security.mobile;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 手机短信登录，UserDetailsService
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface MobileUserDetailsService {

    /**
     * 通过手机号加载用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     * @throws UsernameNotFoundException 不存在异常
     */
    UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;
}
