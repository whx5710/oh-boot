package com.finn.sys.base.service;

import com.finn.support.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface SysUserDetailsService {

    /**
     * 获取 UserDetails 对象
     */
    UserDetails getUserDetails(UserEntity userEntity);
}
