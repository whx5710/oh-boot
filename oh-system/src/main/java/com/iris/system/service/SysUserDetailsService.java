package com.iris.system.service;

import com.iris.system.entity.SysUserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface SysUserDetailsService {

    /**
     * 获取 UserDetails 对象
     */
    UserDetails getUserDetails(SysUserEntity userEntity);
}
