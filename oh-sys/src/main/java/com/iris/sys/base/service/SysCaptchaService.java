package com.iris.sys.base.service;

import com.iris.sys.base.vo.SysCaptchaVO;

/**
 * 验证码
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysCaptchaService {
    /**
     * 生成验证码
     */
    SysCaptchaVO generate();

    /**
     * 验证码效验
     *
     * @param key  key
     * @param code 验证码
     * @return true：成功  false：失败
     */
    boolean validate(String key, String code);
}
