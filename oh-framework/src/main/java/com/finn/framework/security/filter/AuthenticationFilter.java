package com.finn.framework.security.filter;

import com.finn.core.utils.Tools;
import com.finn.framework.common.properties.MultiTenantProperties;
import com.finn.framework.utils.TenantContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.finn.framework.security.cache.TokenStoreCache;
import com.finn.framework.security.user.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 *
 * 1、认证过滤器
 * 2、注入用户信息
 * 3、租户信息拦截
 * @author 王小费 whx5710@qq.com
 * 
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final TokenStoreCache tokenStoreCache;
    private final MultiTenantProperties multiTenantProperties;

    public AuthenticationFilter(TokenStoreCache tokenStoreCache, MultiTenantProperties multiTenantProperties){
        this.tokenStoreCache = tokenStoreCache;
        this.multiTenantProperties = multiTenantProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String accessToken = Tools.getAccessToken(request);
        // accessToken为空-未登录
        if (accessToken == null || accessToken.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        // 获取登录用户信息
        UserDetail user = tokenStoreCache.getUser(accessToken);
        if (user == null) {
            chain.doFilter(request, response);
            return;
        }
        // 用户存在，注入用户信息
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        // 新建 SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        // 租户ID
        if(multiTenantProperties.isEnable()){
            try{
                // 租户ID
                log.debug("拦截请求租户: {} {}", user.getUsername(), user.getTenantId());
                TenantContextHolder.setTenant(user.getTenantId());
                chain.doFilter(request, response);
            }finally {
                TenantContextHolder.clear();
            }
        }else{
            chain.doFilter(request, response);
        }
    }
}
