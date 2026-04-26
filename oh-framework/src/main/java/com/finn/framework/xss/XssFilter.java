package com.finn.framework.xss;

import com.finn.framework.common.properties.XssProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Xss 过滤器
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class XssFilter extends OncePerRequestFilter {
    private final XssProperties properties;
    private final PathMatcher pathMatcher;

    public XssFilter(XssProperties properties, PathMatcher pathMatcher) {
        this.properties = properties;
        this.pathMatcher = pathMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // 优化：只对需要处理的请求包装（POST/PUT/PATCH请求且Content-Type包含表单或JSON）
        if (shouldWrapRequest(request)) {
            filterChain.doFilter(new XssRequestWrapper(request), response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 判断是否需要包装请求
     * @param request 请求
     * @return 是否需要包装
     */
    private boolean shouldWrapRequest(HttpServletRequest request) {
        String method = request.getMethod();
        String contentType = request.getContentType();
        // 只处理有请求体的方法
        if (!"POST".equalsIgnoreCase(method) && !"PUT".equalsIgnoreCase(method) && !"PATCH".equalsIgnoreCase(method)) {
            return false;
        }
        // 只处理表单和JSON请求
        if (contentType == null) {
            return false;
        }
        contentType = contentType.toLowerCase();
        return contentType.contains("application/x-www-form-urlencoded") ||
               contentType.contains("multipart/form-data") ||
               contentType.contains("application/json");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 放行不过滤的URL
        return properties.getExcludeUrls().stream().anyMatch(excludeUrl -> pathMatcher.match(excludeUrl, request.getRequestURI()));
    }

}
