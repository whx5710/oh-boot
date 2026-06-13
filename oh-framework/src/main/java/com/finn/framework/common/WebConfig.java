package com.finn.framework.common;

import com.finn.framework.common.properties.CorsProperties;
import com.finn.framework.exception.TraceIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

    private final CorsProperties corsProperties;

    public WebConfig(CorsProperties corsProperties){
        this.corsProperties = corsProperties;
    }

    /**
     * 注册TraceId拦截器，拦截所有请求
     * @param registry r
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdInterceptor()).addPathPatterns("/**");
    }

    /**
     * 跨域配置
     * @param registry r
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsProperties.getAllowedOrigins().split(",")) // 解析逗号分隔的域名
                .allowedMethods(corsProperties.getAllowedMethods().split(",")) // 请求方法
                .allowedHeaders(corsProperties.getAllowedHeaders())
                .allowCredentials(corsProperties.isAllowCredentials())
                .maxAge(corsProperties.getMaxAge());
    }
}