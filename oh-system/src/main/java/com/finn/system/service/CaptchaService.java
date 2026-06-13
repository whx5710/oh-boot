package com.finn.system.service;

import com.finn.system.vo.CaptchaVO;

/**
 * 验证码
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface CaptchaService {

    /**
     * 自定义工具类生成验证码
     * 平台key,默认PC端 电脑端：PC 移动app端：APP
     */
    CaptchaVO generateImg(String platformKey);

    /**
     * 验证码效验
     * @param platformKey 平台key 电脑浏览器端：PC 移动app端：APP
     * @param key  key
     * @param code 验证码
     * @return true：成功  false：失败
     */
    boolean validate(String platformKey,String key, String code);
}
