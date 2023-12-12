package com.iris.security.service;

import com.iris.system.service.SysUserDetailsService;
import com.iris.framework.security.mobile.MobileUserDetailsService;
import com.iris.system.dao.SysUserDao;
import com.iris.system.entity.SysUserEntity;
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
    private final SysUserDao sysUserDao;

    public MobileUserDetailsServiceImpl(SysUserDetailsService sysUserDetailsService, SysUserDao sysUserDao) {
        this.sysUserDetailsService = sysUserDetailsService;
        this.sysUserDao = sysUserDao;
    }


    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserDao.getByMobile(mobile);
        if (userEntity == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }

        return sysUserDetailsService.getUserDetails(userEntity);
    }

}
