package com.iris.framework.common.xss;

import com.iris.framework.common.config.properties.XssProperties;
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
        filterChain.doFilter(new XssRequestWrapper(request), response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 放行不过滤的URL
        return properties.getExcludeUrls().stream().anyMatch(excludeUrl -> pathMatcher.match(excludeUrl, request.getRequestURI()));
    }

}
