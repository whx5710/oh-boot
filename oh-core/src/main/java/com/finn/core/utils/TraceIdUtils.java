package com.finn.core.utils;

/**
 * TraceId工具类
 * 用于生成和管理全局的traceId，确保整个请求链路都有相同的traceId
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class TraceIdUtils {
    
    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();
    
    /**
     * 生成traceId
     * @return traceId
     */
    public static String generateTraceId() {
        return Tools.generator();
    }
    
    /**
     * 获取当前线程的traceId
     * @return traceId
     */
    public static String getTraceId() {
        String traceId = TRACE_ID.get();
        if (traceId == null) {
            traceId = generateTraceId();
            TRACE_ID.set(traceId);
        }
        return traceId;
    }
    
    /**
     * 设置traceId
     * @param traceId traceId
     */
    public static void setTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }
    
    /**
     * 清除traceId
     */
    public static void clearTraceId() {
        TRACE_ID.remove();
    }
}