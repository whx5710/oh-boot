package com.finn.sys.sms.service;

import java.util.Map;

/**
 * 短信服务API
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SmsApi {

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param params 参数
     * @return 是否发送成功
     */
    boolean send(String mobile, Map<String, String> params);

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param key    参数KEY
     * @param value  参数Value
     * @return 是否发送成功
     */
    boolean sendCode(String mobile, String key, String value);

    /**
     * 效验短信验证码
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return 是否效验成功
     */
    boolean verifyCode(String mobile, String code);
}
