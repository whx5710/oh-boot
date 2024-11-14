package com.iris.sys.security.event;

import com.iris.core.constant.Constant;
import com.iris.framework.security.user.UserDetail;
import com.iris.sys.base.enums.LoginOperationEnum;
import com.iris.sys.base.service.SysLogLoginService;
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
    private final SysLogLoginService sysLogLoginService;

    public AuthenticationEvents(SysLogLoginService sysLogLoginService) {
        this.sysLogLoginService = sysLogLoginService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        // 用户信息
        UserDetail user = (UserDetail) event.getAuthentication().getPrincipal();

        // 保存登录日志
        sysLogLoginService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGIN_SUCCESS.getValue());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        // 用户名
        String username = (String) event.getAuthentication().getPrincipal();

        // 保存登录日志
        sysLogLoginService.save(username, Constant.FAIL, LoginOperationEnum.ACCOUNT_FAIL.getValue());
    }

}