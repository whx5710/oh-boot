package com.iris.system.security.service;

import com.iris.system.base.service.SysUserDetailsService;
import com.iris.system.base.mapper.SysUserMapper;
import com.iris.system.base.entity.SysUserEntity;
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
    private final SysUserMapper sysUserMapper;

    public UserDetailsServiceImpl(SysUserDetailsService sysUserDetailsService, SysUserMapper sysUserMapper) {
        this.sysUserDetailsService = sysUserDetailsService;
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserMapper.getByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return sysUserDetailsService.getUserDetails(userEntity);
    }

}
