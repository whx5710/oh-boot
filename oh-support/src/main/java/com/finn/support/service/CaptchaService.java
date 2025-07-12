package com.finn.support.service;

import com.finn.support.vo.CaptchaVO;

/**
 * 验证码
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface CaptchaService {

    /**
     * 自定义工具类生成验证码
     */
    CaptchaVO generateImg();

    /**
     * 验证码效验
     *
     * @param key  key
     * @param code 验证码
     * @return true：成功  false：失败
     */
    boolean validate(String key, String code);
}
