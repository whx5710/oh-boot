package com.finn.core.interceptor;

import com.finn.core.utils.TraceIdUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * TraceId拦截器
 * 用于在请求开始时生成traceId并在响应中返回
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class TraceIdInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取traceId，如果没有则生成新的
        String traceId = request.getHeader("X-Trace-Id");
        if (traceId == null || traceId.isEmpty()) {
            traceId = TraceIdUtils.generateTraceId();
        }
        // 设置traceId到线程局部变量
        TraceIdUtils.setTraceId(traceId);
        // 将traceId设置到响应头中
        response.setHeader("X-Trace-Id", traceId);
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除traceId
        TraceIdUtils.clearTraceId();
    }
}