package com.finn.framework.security.wechat;

import com.finn.framework.entity.HashDto;
import com.finn.framework.exception.ServerException;
import com.finn.framework.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author 王小费 whx5710@qq.com
 */
@Component
public class WechatMiniProgramAuthenticationProvider implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(WechatMiniProgramAuthenticationProvider.class);

    // 调用微信接口的客户端
    private final WechatService wechatService;

    public WechatMiniProgramAuthenticationProvider(WechatService wechatService){
        this.wechatService = wechatService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        WechatMiniProgramAuthenticationToken token =
                (WechatMiniProgramAuthenticationToken) authentication;

        String code = Objects.requireNonNull(token.getCredentials()).toString();

        // 1. 向微信服务器验证 code，获取 openid
        String jsonSession = wechatService.code2Session(code);
        HashDto session = JsonUtils.parseObject(jsonSession, HashDto.class);
        String openid = "";
        if(session.containsKey("openid")){
            openid = session.getStr("openid");
        }else {
            log.error("微信接口返回报错：{}", jsonSession);
            throw new ServerException("未获取到openid");
        }
        // 2. 查找或创建用户（微信用户通常没有密码）
        UserDetails user = wechatService.findOrCreateByOpenid(openid);
        System.out.println("查询到的用户 " + user);

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