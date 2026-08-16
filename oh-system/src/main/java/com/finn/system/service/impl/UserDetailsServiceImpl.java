package com.finn.system.service.impl;

import com.finn.system.entity.UserEntity;
import com.finn.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 账号密码登录 UserDetailsService
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
        // 用户类型，0普通用户1微信小程序用户
        UserEntity userEntity = userService.getByUsername(username, "0");
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return userService.getUserDetails(userEntity);
    }

}
