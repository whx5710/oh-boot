package com.finn.sys.security.service;

import com.finn.framework.security.mobile.MobileUserDetailsService;
import com.finn.support.entity.UserEntity;
import com.finn.support.mapper.UserMapper;
import com.finn.sys.base.service.SysUserDetailsService;
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
    private final SysUserDetailsService sysUserDetailsService;
    private final UserMapper userMapper;

    public MobileUserDetailsServiceImpl(SysUserDetailsService sysUserDetailsService, UserMapper userMapper) {
        this.sysUserDetailsService = sysUserDetailsService;
        this.userMapper = userMapper;
    }


    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        UserEntity userEntity = userMapper.getByMobile(mobile);
        if (userEntity == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }

        return sysUserDetailsService.getUserDetails(userEntity);
    }

}
