package com.finn.sys.service.impl;

import com.finn.support.entity.UserEntity;
import com.finn.support.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 账号登录 UserDetailsService
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 系统中租户ID如果为null，都默认成空字符串
        if(userEntity.getTenantId() == null){
            userEntity.setTenantId("");
        }
        return userService.getUserDetails(userEntity);
    }

}
