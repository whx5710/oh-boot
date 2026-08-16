package com.finn.system.service;

import com.finn.common.constant.Constant;
import com.finn.framework.security.user.UserDetail;
import com.finn.system.enums.LoginOperationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(AuthenticationEvents.class);

    private final LoginLogService loginLogService;

    public AuthenticationEvents(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        
        if (!(principal instanceof UserDetail user)) {
            log.warn("非用户信息数据，保存登录日志失败");
            return;
        }
        loginLogService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGIN_SUCCESS.getValue());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        // 用户名
        String username = (String) event.getAuthentication().getPrincipal();

        // 保存登录日志
        loginLogService.save(username, Constant.FAIL, LoginOperationEnum.ACCOUNT_FAIL.getValue());
    }

}