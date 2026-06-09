package com.finn.framework.security.wechat;

import org.springframework.security.core.userdetails.UserDetails;

public interface WechatService {

    /**
     * 使用 code + appid + secret 请求微信接口获取 openid/session_key
     * @param code
     * @return
     */
    String code2Session(String code);

    /**
     * 根据open id 获取用户信息
     * @param openId
     * @return
     */
    UserDetails findOrCreateByOpenid(String openId);
}
