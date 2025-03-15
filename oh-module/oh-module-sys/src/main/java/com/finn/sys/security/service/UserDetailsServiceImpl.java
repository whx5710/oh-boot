package com.finn.sys.security.service;

import com.finn.support.entity.UserEntity;
import com.finn.support.mapper.UserMapper;
import com.finn.sys.base.service.SysUserDetailsService;
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
    private final SysUserDetailsService sysUserDetailsService;
    private final UserMapper userMapper;

    public UserDetailsServiceImpl(SysUserDetailsService sysUserDetailsService, UserMapper userMapper) {
        this.sysUserDetailsService = sysUserDetailsService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userMapper.getByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return sysUserDetailsService.getUserDetails(userEntity);
    }

}
