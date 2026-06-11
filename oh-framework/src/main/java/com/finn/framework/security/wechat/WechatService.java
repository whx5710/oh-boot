package com.finn.framework.security.wechat;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 如果微信登录，需实现该接口
 *
 * @author 王小费 whx5710@qq.com
 */
public interface WechatService {

    /**
     * 使用 code + appid + secret 请求微信接口获取 openid/session_key
     * @param code code
     * @return 微信接口返回的结果串
     */
    String code2Session(String code);

    /**
     * 根据open id 获取用户信息，如果无该用户，则新增
     * @param openId
     * @return user info
     */
    UserDetails findOrCreateByOpenid(String openId);
}
