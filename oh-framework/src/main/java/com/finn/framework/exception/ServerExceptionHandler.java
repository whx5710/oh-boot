package com.finn.framework.exception;

import com.finn.core.exception.ErrorCode;
import com.finn.core.exception.FinnExceptionHandler;
import com.finn.core.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 异常处理器
 * 对异常信息进行统一处理
 * @author 王小费 whx5710@qq.com
 *
 */
@RestControllerAdvice
public class ServerExceptionHandler extends FinnExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ServerExceptionHandler.class);

    /**
     * 403 没有权限，禁止访问
     * @param ex e
     * @return 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> handleAccessDeniedException(Exception ex) {
        log.error("没有权限，禁止访问！{}",ex.getMessage());
        return Result.error(ErrorCode.FORBIDDEN);
    }

}