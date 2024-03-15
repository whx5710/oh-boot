package com.iris.system.security.service;

import com.iris.system.pim.service.SysUserDetailsService;
import com.iris.system.pim.dao.SysUserDao;
import com.iris.system.pim.entity.SysUserEntity;
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
    private final SysUserDao sysUserDao;

    public UserDetailsServiceImpl(SysUserDetailsService sysUserDetailsService, SysUserDao sysUserDao) {
        this.sysUserDetailsService = sysUserDetailsService;
        this.sysUserDao = sysUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserDao.getByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return sysUserDetailsService.getUserDetails(userEntity);
    }

}
