package com.iris.framework.security.config;

import com.iris.framework.security.exception.SecurityAuthenticationEntryPoint;
import com.iris.framework.security.filter.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Spring SecurityFilter 过滤文件
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterConfig {
    private final AuthenticationFilter authenticationFilter;
    private final PermitResource permitResource;

    public SecurityFilterConfig(AuthenticationFilter authenticationFilter, PermitResource permitResource) {
        this.authenticationFilter = authenticationFilter;
        this.permitResource = permitResource;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 忽略授权的地址列表
        List<String> permitList = permitResource.getPermitList();
        String[] permits = permitList.toArray(new String[0]);
        // 相关配置请查看官方例子
        // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(permits).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling((authorizeHttpRequests) -> authorizeHttpRequests.authenticationEntryPoint(new SecurityAuthenticationEntryPoint()))
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
        ;
        return http.build();
    }
}
