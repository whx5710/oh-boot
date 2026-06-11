package com.finn.system.service.impl;

import com.finn.framework.security.wechat.WechatService;
import com.finn.framework.utils.HttpUtil;
import com.finn.system.config.WechatProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class WechatServiceImpl implements WechatService {

    private final WechatProperties wechatProperties;

    public WechatServiceImpl(WechatProperties wechatProperties){
        this.wechatProperties = wechatProperties;
    }

    @Override
    public String code2Session(String code) {
        String url = String.format("%s/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                wechatProperties.getApi(), wechatProperties.getApi(), wechatProperties.getSecret(), code);
        return HttpUtil.get(url);
    }

    @Override
    public UserDetails findOrCreateByOpenid(String openId) {
        System.out.println("根据open id 新增或查询用户");
        return null;
    }

}
