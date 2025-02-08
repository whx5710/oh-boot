package com.finn.core.exception;

import com.finn.core.utils.Result;
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
        log.error("Validator校验不正确! {}", fieldError.getDefaultMessage());
        return Result.error(fieldError.getDefaultMessage());
    }

    /**
     * 未找到资源
     * @param ex e
     * @return 404
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<String> handleNotFoundException(Exception ex) {
        log.error("找不到资源！{}", ex.getMessage(), ex);
        return Result.error(ErrorCode.NOT_FOUND);
    }

    /**
     * 缺少请求参数错误
     * @param ex e
     * @return 500
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<String> handleMissingException(Exception ex) {
        log.error("请求参数异常！{}", ex.getMessage(), ex);
        return Result.error(ErrorCode.MISSING_PARAMETER_ERROR);
    }

    /**
     * 消息不可读错误
     * @param ex e
     * @return 500
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<String> handleNotReadableException(Exception ex) {
        log.error("消息不可读！{}", ex.getMessage(), ex);
        return Result.error(ErrorCode.HTTP_MSG_NOT_READABLE);
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
        log.error("系统出现异常！{}", ex.getMessage(), ex);
        return Result.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}