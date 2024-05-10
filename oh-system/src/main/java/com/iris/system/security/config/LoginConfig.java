package com.iris.system.security.config;

import com.iris.system.sms.service.SmsApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 登录配置信息
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Configuration
public class LoginConfig {

    @Bean
    @ConditionalOnMissingBean
    SmsApi smsApi() {
        return new SmsApi() {
            @Override
            public boolean send(String mobile, Map<String, String> params) {
                return false;
            }

            @Override
            public boolean sendCode(String mobile, String key, String value) {
                return false;
            }

            @Override
            public boolean verifyCode(String mobile, String code) {
                return false;
            }
        };
    }
}
