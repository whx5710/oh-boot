package com.finn.system.config;

import com.finn.framework.security.mobile.MobileAuthenticationProvider;
import com.finn.framework.security.wechat.WechatMiniProgramAuthenticationProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * SpringSecurity 配置文件
 * 认证方式：账号密码、手机验证码、微信认证
 * @author 王小费 whx5710@qq.com
 *
 */
@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final MobileAuthenticationProvider mobileAuthenticationProvider;
    private final WechatMiniProgramAuthenticationProvider wechatMiniProgramAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    public SecurityConfig(UserDetailsService userDetailsService,
                          MobileAuthenticationProvider mobileAuthenticationProvider,
                          WechatMiniProgramAuthenticationProvider wechatMiniProgramAuthenticationProvider,
                          PasswordEncoder passwordEncoder, ApplicationEventPublisher applicationEventPublisher) {

        this.userDetailsService = userDetailsService;
        this.mobileAuthenticationProvider = mobileAuthenticationProvider;
        this.wechatMiniProgramAuthenticationProvider = wechatMiniProgramAuthenticationProvider;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    /**
     * 手机验证码登录
     * @return
     */
//    @Bean
//    MobileAuthenticationProvider mobileAuthenticationProvider() {
//        return new MobileAuthenticationProvider(mobileVerifyCodeService);
//    }

    // 认证管理器
    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providerList = new ArrayList<>();
        providerList.add(daoAuthenticationProvider());
        providerList.add(mobileAuthenticationProvider); // 手机验证码登录
        providerList.add(wechatMiniProgramAuthenticationProvider); // 微信小程序登录

        ProviderManager providerManager = new ProviderManager(providerList);
        // 事件注册，可监听登录事件，记录登录日志
        providerManager.setAuthenticationEventPublisher(new DefaultAuthenticationEventPublisher(applicationEventPublisher));
        return providerManager;
    }
}
