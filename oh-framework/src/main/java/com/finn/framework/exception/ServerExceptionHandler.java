package com.finn.framework.exception;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.common.enums.ErrorCode;
import com.finn.framework.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 对异常信息进行统一处理
 * @author 王小费 whx5710@qq.com
 *
 */
@RestControllerAdvice
public class ServerExceptionHandler extends SuperExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ServerExceptionHandler.class);

    public ServerExceptionHandler(RedisCache redisCache) {
        super(redisCache);
    }

    /**
     * 403 没有权限，禁止访问
     * @param ex e
     * @return 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> handleAccessDeniedException(Exception ex) {
        String traceId = TraceIdUtils.getTraceId();
        log.error("没有权限，禁止访问！{} [{}]", ex.getMessage(), traceId);
        if(ex instanceof AuthorizationDeniedException ade){
            log.error("未授权标识 {} [{}]", ade.getAuthorizationResult(), traceId);
        }
        return Result.error(ErrorCode.FORBIDDEN.getCode(), ErrorCode.FORBIDDEN.getMsg());
    }

}