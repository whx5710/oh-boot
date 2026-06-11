package com.finn.framework.security.wechat;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 自定义 Token，仅携带微信登录凭证
 * 用户点击微信登录
 *     ↓
 * 小程序调用 wx.login() 获取 code
 *     ↓
 * 前端将 code 发送到后端
 *     ↓
 * 后端用 code + appid + secret 请求微信接口获取 openid/session_key
 *     ↓
 * 后端根据 openid 查找或创建用户
 *     ↓
 * 后端生成 JWT Token 返回给前端
 *     ↓
 * 前端存储 token，后续请求携带 Authorization: Bearer <token>
 *  @author 王小费 whx5710@qq.com
 */
public class WechatMiniProgramAuthenticationToken extends AbstractAuthenticationToken {

    private String code;           // 微信临时登录凭证
    private final String openId;   // 微信用户唯一标识（认证后填充）
    private final UserDetails userDetails; // 用户详情（认证后填充）

    // 未认证状态（构造时）
    public WechatMiniProgramAuthenticationToken(String code) {
        super((Collection<? extends GrantedAuthority>) null);
        this.code = code;
        this.openId = null;
        this.userDetails = null;
        setAuthenticated(false);
    }

    // 已认证状态（认证成功后）
    public WechatMiniProgramAuthenticationToken(String openId, UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.code = null;
        this.openId = openId;
        this.userDetails = userDetails;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() { return code; }

    @Override
    public Object getPrincipal() { return userDetails; }

    public String getOpenId() { return openId; }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.code = null;
    }
}