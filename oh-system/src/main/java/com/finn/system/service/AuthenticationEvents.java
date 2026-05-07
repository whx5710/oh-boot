package com.finn.system.service;

import com.finn.framework.common.constant.Constant;
import com.finn.framework.security.user.UserDetail;
import com.finn.system.enums.LoginOperationEnum;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * 认证事件处理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class AuthenticationEvents {
    private final LoginLogService loginLogService;

    public AuthenticationEvents(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        // 用户信息
        UserDetail user = (UserDetail) event.getAuthentication().getPrincipal();

        // 保存登录日志
        if(user != null){
            loginLogService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGIN_SUCCESS.getValue(), user.getTenantId());
        }
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        // 用户名
        String username = (String) event.getAuthentication().getPrincipal();

        // 保存登录日志
        loginLogService.save(username, Constant.FAIL, LoginOperationEnum.ACCOUNT_FAIL.getValue(), null);
    }

}