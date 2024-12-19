package com.iris.sys.security.service;

import com.iris.framework.security.mobile.MobileUserDetailsService;
import com.iris.support.entity.SysUserEntity;
import com.iris.support.mapper.SysUserMapper;
import com.iris.sys.base.service.SysUserDetailsService;
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
    private final SysUserMapper sysUserMapper;

    public MobileUserDetailsServiceImpl(SysUserDetailsService sysUserDetailsService, SysUserMapper sysUserMapper) {
        this.sysUserDetailsService = sysUserDetailsService;
        this.sysUserMapper = sysUserMapper;
    }


    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserMapper.getByMobile(mobile);
        if (userEntity == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }

        return sysUserDetailsService.getUserDetails(userEntity);
    }

}
