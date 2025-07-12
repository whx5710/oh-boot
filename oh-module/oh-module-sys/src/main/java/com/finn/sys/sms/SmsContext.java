package com.finn.sys.sms;

import com.finn.core.exception.ServerException;
import com.finn.sys.base.enums.SmsPlatformEnum;
import com.finn.sys.sms.config.SmsConfig;
import com.finn.sys.sms.service.SmsStrategy;

import java.util.Map;

/**
 * 短信 Context
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class SmsContext {
    private final SmsStrategy smsStrategy;

    public SmsContext(SmsConfig config) {
//        if (config.getPlatform() == SmsPlatformEnum.ALIYUN.getValue()) {
//            this.smsStrategy = new AliyunSmsStrategy(config);
//        } else if (config.getPlatform() == SmsPlatformEnum.TENCENT.getValue()) {
//            this.smsStrategy = new TencentSmsStrategy(config);
//        } else if (config.getPlatform() == SmsPlatformEnum.QINIU.getValue()) {
//            this.smsStrategy = new QiniuSmsStrategy(config);
//        } else if (config.getPlatform() == SmsPlatformEnum.HUAWEI.getValue()) {
//            this.smsStrategy = new HuaweiSmsStrategy(config);
//        } else {
//            throw new ServerException("未知的短信平台");
//        }
        if (config.getPlatform() == SmsPlatformEnum.HUAWEI.getValue()) {
            this.smsStrategy = null;//new HuaweiSmsStrategy(config);
        } else {
            throw new ServerException("未知的短信平台");
        }
    }

    public void send(String mobile, Map<String, String> params) {
        smsStrategy.send(mobile, params);
    }
}
