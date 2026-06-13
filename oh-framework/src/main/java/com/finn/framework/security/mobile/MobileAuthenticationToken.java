package com.finn.framework.security.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 手机短信验证码登录 AuthenticationToken
 * 由于用户可能是平台用户的同时，还是第三方平台用户（微信、支付宝小程序等其他第三方平台），手机号码相同，
 * 因此，用手机验证码登录时，需确定用户源于哪个平台登录
 * @author 王小费 whx5710@qq.com
 * 
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private String code;
    /**
     * 0普通用户1微信小程序用户
     */
    private String userType;

    // 未认证状态（构造时）
    public MobileAuthenticationToken(Object principal, String code, String userType) {
        super((Collection<? extends GrantedAuthority>) null);
        this.principal = principal;
        this.code = code;
        this.userType = userType;
        setAuthenticated(false);
    }

    // 已认证状态（认证成功后）
    public MobileAuthenticationToken(Object principal, String code, String userType, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.code = code;
        this.userType = userType;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.code;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getUserType(){
        return this.userType;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.code = null;
        this.userType = null;
    }
}
