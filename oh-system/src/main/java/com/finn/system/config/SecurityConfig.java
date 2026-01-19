package com.finn.system.config;

import com.finn.framework.security.mobile.MobileAuthenticationProvider;
import com.finn.framework.security.mobile.MobileUserDetailsService;
import com.finn.framework.security.mobile.MobileVerifyCodeService;
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
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final MobileUserDetailsService mobileUserDetailsService;
    private final MobileVerifyCodeService mobileVerifyCodeService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    public SecurityConfig(UserDetailsService userDetailsService,
                          MobileUserDetailsService mobileUserDetailsService, MobileVerifyCodeService mobileVerifyCodeService,
                          PasswordEncoder passwordEncoder, ApplicationEventPublisher applicationEventPublisher) {

        this.userDetailsService = userDetailsService;
        this.mobileUserDetailsService = mobileUserDetailsService;
        this.mobileVerifyCodeService = mobileVerifyCodeService;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    MobileAuthenticationProvider mobileAuthenticationProvider() {
        return new MobileAuthenticationProvider(mobileUserDetailsService, mobileVerifyCodeService);
    }

    // 认证管理器
    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providerList = new ArrayList<>();
        providerList.add(daoAuthenticationProvider());
        providerList.add(mobileAuthenticationProvider());

        ProviderManager providerManager = new ProviderManager(providerList);
        // 事件注册，可监听登录事件，记录登录日志
        providerManager.setAuthenticationEventPublisher(new DefaultAuthenticationEventPublisher(applicationEventPublisher));
        return providerManager;
    }
}
