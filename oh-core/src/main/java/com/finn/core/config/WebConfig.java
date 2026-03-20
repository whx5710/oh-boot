package com.finn.core.config;

import com.finn.core.interceptor.TraceIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 用于注册拦截器等Web相关配置
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册TraceId拦截器，拦截所有请求
        registry.addInterceptor(new TraceIdInterceptor()).addPathPatterns("/**");
    }
}