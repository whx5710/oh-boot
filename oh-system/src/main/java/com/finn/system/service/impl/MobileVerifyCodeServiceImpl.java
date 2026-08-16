package com.finn.system.service.impl;

import com.finn.framework.security.mobile.MobileVerifyCodeService;
import com.finn.system.entity.UserEntity;
import com.finn.system.mapper.UserMapper;
import com.finn.system.service.SmsApi;
import com.finn.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 短信验证码效验
 * 由于用户可能是平台用户的同时，还是第三方平台用户（微信、支付宝小程序等其他第三方平台），手机号码相同，
 * 因此，用手机验证码登录时，需确定用户源于哪个平台登录
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class MobileVerifyCodeServiceImpl implements MobileVerifyCodeService {
    private final SmsApi smsApi;

    private final UserService userService;
    private final UserMapper userMapper;

    public MobileVerifyCodeServiceImpl(SmsApi smsApi, UserService userService, UserMapper userMapper) {
        this.smsApi = smsApi;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public boolean verifyCode(String mobile, String code) {
        return smsApi.verifyCode(mobile, code);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile, String userType) throws UsernameNotFoundException {
        // userType 用于哪个平台的用户判断
        UserEntity userEntity = userMapper.getByMobile(mobile);
        if (userEntity == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }
        return userService.getUserDetails(userEntity);
    }
}
