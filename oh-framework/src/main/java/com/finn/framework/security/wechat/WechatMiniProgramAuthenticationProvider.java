package com.finn.framework.security.wechat;

import com.finn.framework.entity.HashDto;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.mobile.MobileAuthenticationToken;
import com.finn.framework.utils.JsonUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Component
public class WechatMiniProgramAuthenticationProvider implements AuthenticationProvider {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    // 调用微信接口的客户端
    private final WechatService wechatService;

    public WechatMiniProgramAuthenticationProvider(WechatService wechatService){
        this.wechatService = wechatService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        Assert.isInstanceOf(MobileAuthenticationToken.class, authentication,
                () -> messages.getMessage(
                        "WechatMiniProgramAuthenticationProvider.onlySupports",
                        "Only WechatMiniProgramAuthenticationProvider is supported"));
        WechatMiniProgramAuthenticationToken token =
                (WechatMiniProgramAuthenticationToken) authentication;

        String code = token.getCredentials().toString();

        // 1. 向微信服务器验证 code，获取 openid
        String jsonSession = wechatService.code2Session(code);
        HashDto session = JsonUtils.parseObject(jsonSession, HashDto.class);
        String openid = "";
        if(session.containsKey("openid")){
            openid = session.getStr("openid");
        }else {
            throw new ServerException("未获取到openid");
        }
        // 2. 查找或创建用户（微信用户通常没有密码）
        UserDetails user = wechatService.findOrCreateByOpenid(openid);

        // 3. 加载用户权限
        List<GrantedAuthority> authorities =
                AuthorityUtils.createAuthorityList("ROLE_USER");

        // 4. 返回已认证 Token
        return new WechatMiniProgramAuthenticationToken(openid, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatMiniProgramAuthenticationToken.class.isAssignableFrom(authentication);
    }
}