package com.finn.framework.exception;

import com.finn.framework.cache.ErrorLogCache;
import com.finn.framework.common.enums.ErrorCode;
import com.finn.framework.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 异常处理器
 * 对异常信息进行统一处理
 * @author 王小费 whx5710@qq.com
 *
 */
@RestControllerAdvice
public class SuperExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(SuperExceptionHandler.class);

    private final ErrorLogCache errorLogCache;

    public SuperExceptionHandler(ErrorLogCache errorLogCache){
        this.errorLogCache = errorLogCache;
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServerException.class)
    public Result<String> handleException(ServerException ex) {
        log.error("系统异常！[{}] {}", TraceIdUtils.getTraceId(), ex.getMessage(), ex);
        return Result.error(ex.getCode(), ex.getMsg(), ex.getStackInfo());
    }

    /**
     * SpringMVC参数绑定，Validator校验不正确
     */
    @ExceptionHandler(BindException.class)
    public Result<String> bindException(BindException ex) {
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null;
        log.error("Validator校验不正确![{}]  {}", TraceIdUtils.getTraceId(), fieldError.getDefaultMessage());
        return Result.error(fieldError.getDefaultMessage());
    }

    /**
     * 未找到资源
     * @param ex e
     * @return 404
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<String> handleNotFoundException(Exception ex) {
        log.error("找不到资源！[{}] {}", TraceIdUtils.getTraceId(), ex.getMessage(), ex);
        return Result.error(ErrorCode.NOT_FOUND, ex.getMessage());
    }

    /**
     * 缺少请求参数错误
     * @param ex e
     * @return 500
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<String> handleMissingException(Exception ex) {
        log.error("请求参数异常！[{}] {}", TraceIdUtils.getTraceId(), ex.getMessage(), ex);
        return Result.error(ErrorCode.MISSING_PARAMETER_ERROR, ex.getMessage());
    }

    /**
     * 消息不可读错误
     * @param ex e
     * @return 500
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<String> handleNotReadableException(Exception ex) {
        log.error("无法正确解析或消息不可读！[{}] {}", TraceIdUtils.getTraceId(), ex.getMessage(), ex);
        Result<String> result = Result.error(ErrorCode.HTTP_MSG_NOT_READABLE.getCode(), ErrorCode.HTTP_MSG_NOT_READABLE.getMsg());
        // 缓存错误日志
        errorLogCache.cacheLog(result, ex);
        return result;
    }

    /**
     * 未捕获的异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
        log.error("系统出现异常！[{}] {}", TraceIdUtils.getTraceId(), ex.getMessage().strip(), ex);
        Result<String> result = Result.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMsg());
        // 缓存错误日志
        errorLogCache.cacheLog(result, ex);
        return result;
    }
}