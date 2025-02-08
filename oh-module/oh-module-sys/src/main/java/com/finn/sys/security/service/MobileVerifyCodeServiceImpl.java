package com.finn.sys.security.service;

import com.finn.framework.security.mobile.MobileVerifyCodeService;
import com.finn.sys.sms.service.SmsApi;
import org.springframework.stereotype.Service;

/**
 * 短信验证码效验
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class MobileVerifyCodeServiceImpl implements MobileVerifyCodeService {
    private final SmsApi smsApi;

    public MobileVerifyCodeServiceImpl(SmsApi smsApi) {
        this.smsApi = smsApi;
    }

    @Override
    public boolean verifyCode(String mobile, String code) {
        return smsApi.verifyCode(mobile, code);
    }
}
