package com.iris.framework.security.filter;

import com.iris.core.utils.IrisTools;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.iris.framework.security.cache.TokenStoreCache;
import com.iris.framework.security.user.UserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 认证过滤器
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final TokenStoreCache tokenStoreCache;

    public AuthenticationTokenFilter(TokenStoreCache tokenStoreCache){
        this.tokenStoreCache = tokenStoreCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String accessToken = IrisTools.getAccessToken(request);
        // accessToken为空，表示未登录
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
        // 用户存在
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        // 新建 SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }
}
