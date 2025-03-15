package com.finn.sys.security.event;

import com.finn.core.constant.Constant;
import com.finn.framework.security.user.UserDetail;
import com.finn.sys.base.enums.LoginOperationEnum;
import com.finn.sys.base.service.LogLoginService;
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
    private final LogLoginService logLoginService;

    public AuthenticationEvents(LogLoginService logLoginService) {
        this.logLoginService = logLoginService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        // 用户信息
        UserDetail user = (UserDetail) event.getAuthentication().getPrincipal();

        // 保存登录日志
        logLoginService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGIN_SUCCESS.getValue(), user.getTenantId());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        // 用户名
        String username = (String) event.getAuthentication().getPrincipal();

        // 保存登录日志
        logLoginService.save(username, Constant.FAIL, LoginOperationEnum.ACCOUNT_FAIL.getValue(), null);
    }

}