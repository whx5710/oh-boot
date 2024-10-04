package com.iris.framework.common.exception;

import com.iris.framework.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
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
public class ServerExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ServerExceptionHandler.class);

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServerException.class)
    public Result<String> handleException(ServerException ex) {
        return Result.error(ex.getCode(), ex.getMsg());
    }

    /**
     * SpringMVC参数绑定，Validator校验不正确
     */
    @ExceptionHandler(BindException.class)
    public Result<String> bindException(BindException ex) {
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null;
        return Result.error(fieldError.getDefaultMessage());
    }

    /**
     * 403 没有权限，禁止访问
     * @param ex e
     * @return 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> handleAccessDeniedException(Exception ex) {
        return Result.error(ErrorCode.FORBIDDEN);
    }

    /**
     * 未找到资源
     * @param ex e
     * @return 404
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<String> handleNotFoundException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(ErrorCode.NOT_FOUND);
    }

    /**
     * 缺少请求参数错误
     * @param ex e
     * @return 500
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<String> handleMissingException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(ErrorCode.MISSING_PARAMETER_ERROR);
    }

    /**
     * 消息不可读错误
     * @param ex e
     * @return 500
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<String> handleNotReadableException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(ErrorCode.HTTP_MSG_NOT_READABLE);
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}