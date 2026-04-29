package com.finn.framework.exception;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.common.enums.ErrorCode;
import com.finn.framework.entity.HashDto;
import com.finn.framework.entity.Result;
import com.finn.framework.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

import static com.finn.framework.cache.RedisKeys.PREFIX;
import static com.finn.framework.utils.DateUtils.DATE_TIME_MIL_PATTERN;


/**
 * 异常处理器
 * 对异常信息进行统一处理
 * @author 王小费 whx5710@qq.com
 *
 */
@RestControllerAdvice
public class SuperExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(SuperExceptionHandler.class);

    private final RedisCache redisCache;

    public SuperExceptionHandler(RedisCache redisCache){
        this.redisCache = redisCache;
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
        log.error("消息不可读！[{}] {}", TraceIdUtils.getTraceId(), ex.getMessage(), ex);
        return Result.error(ErrorCode.HTTP_MSG_NOT_READABLE, ex.getMessage());
    }

    /**
     * 异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
        String msg = ex.getMessage().strip();
        log.error("系统出现异常！[{}] {}", TraceIdUtils.getTraceId(), msg, ex);
        Result<String> result = Result.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMsg());

        // 默认缓存1小时
        HashDto dto = new HashDto();
        dto.put("stackInfo", msg);
        dto.put("errTime", DateUtils.format(LocalDateTime.now(), DATE_TIME_MIL_PATTERN));
        dto.put("errCode", result.getCode());
        dto.put("msg", result.getMsg());
        dto.put("traceId", TraceIdUtils.getTraceId());
        redisCache.leftPush(PREFIX + "error:msg", dto.toJson(), 3600);
        return result;
    }
}