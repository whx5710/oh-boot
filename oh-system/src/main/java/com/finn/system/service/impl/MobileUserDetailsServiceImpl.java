package com.finn.system.service.impl;

import com.finn.framework.security.mobile.MobileUserDetailsService;
import com.finn.system.entity.UserEntity;
import com.finn.system.mapper.UserMapper;
import com.finn.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 手机验证码登录 MobileUserDetailsService
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class MobileUserDetailsServiceImpl implements MobileUserDetailsService {
    private final UserService userService;
    private final UserMapper userMapper;

    public MobileUserDetailsServiceImpl(UserMapper userMapper, UserService userService) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        UserEntity userEntity = userMapper.getByMobile(mobile);
        if (userEntity == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }
        return userService.getUserDetails(userEntity);
    }

}
