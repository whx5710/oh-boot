package com.finn.framework.security.wechat;

import com.finn.framework.entity.HashDto;
import com.finn.framework.exception.ServerException;
import com.finn.framework.utils.AssertUtils;
import com.finn.framework.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
 * 后端生成 Token 返回给前端
 *     ↓
 * 前端存储 token，后续请求携带 Authorization: Bearer <token>
 *  @author 王小费 whx5710@qq.com
 */
@Component
public class WechatMiniAuthenticationProvider implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(WechatMiniAuthenticationProvider.class);

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    // 调用微信接口的客户端
    private final WechatMiniService wechatMiniService;

    public WechatMiniAuthenticationProvider(WechatMiniService wechatMiniService){
        this.wechatMiniService = wechatMiniService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        WechatMiniAuthenticationToken token = (WechatMiniAuthenticationToken) authentication;
        AssertUtils.isNull(token.getCredentials(), "微信临时登录凭证code");
        String code = token.getCredentials().toString();

        // 1. 向微信服务器验证 code，获取 openid
        String jsonSession = wechatMiniService.code2Session(code);
        HashDto session = JsonUtils.parseObject(jsonSession, HashDto.class);
        String openid = "";
        if(session.containsKey("openid") && session.get("openid") != null){
            openid = session.getStr("openid");
        }else {
            log.error("微信接口返回报错：{}", jsonSession);
            throw new ServerException("未获取到openid");
        }

        // 2. 查找或创建用户（微信用户通常没有密码）
        UserDetails user = wechatMiniService.findOrCreateByOpenid(openid);

        // 3. 加载用户权限
        Collection<? extends GrantedAuthority> authorities = authoritiesMapper.mapAuthorities(user.getAuthorities());

        // 4. 返回已认证 Token
        WechatMiniAuthenticationToken wechatMiniAuthenticationToken = new WechatMiniAuthenticationToken(openid, user, authorities);
        wechatMiniAuthenticationToken.setDetails(authentication.getDetails());
        return wechatMiniAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatMiniAuthenticationToken.class.isAssignableFrom(authentication);
    }
}