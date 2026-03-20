package com.finn.core.exception;

import com.finn.core.utils.HttpContextUtils;
import com.finn.core.utils.TraceIdUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * 自定义异常
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class ServerException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(ServerException.class);

    /**
     * 错误代码
     */
    private int code;

    private boolean success;
    /**
     * 提示消息
     */
    private String msg;

    /**
     * 堆栈信息
     * 一般不返回到前端，供后台查看错误原因
     */
    private String stackInfo;

    /**
     * 异常追踪ID
     */
    private String traceId;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求方法
     */
    private String requestMethod;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStackInfo() {
        return stackInfo;
    }

    public void setStackInfo(String stackInfo) {
        this.stackInfo = stackInfo;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * 构建异常上下文信息
     */
    private void buildContextInfo() {
        this.traceId = TraceIdUtils.getTraceId();
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (request != null) {
            this.requestUrl = request.getRequestURI();
            this.requestMethod = request.getMethod();
        }
    }

    /**
     * 获取完整堆栈信息
     */
    private String getFullStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    /**
     *
     * @param msg 错误信息提示
     */
    public ServerException(String msg) {
        super(msg);
        buildContextInfo();
        this.code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
        this.stackInfo = msg;
        this.success = false;
        log.error("【{}】[{}] {} {}: {}", code, traceId, requestMethod, requestUrl, msg);
    }

    /**
     *
     * @param msg 提示给前端信息
     * @param stackInfo 堆栈信息
     */
    public ServerException(String msg, String stackInfo) {
        super(msg);
        buildContextInfo();
        this.code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
        this.stackInfo = stackInfo;
        this.success = false;
        log.error("【{}】[{}] {} {}: {} 异常提示:{}", code, traceId, requestMethod, requestUrl, msg, stackInfo);
    }

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        buildContextInfo();
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.stackInfo = errorCode.getMsg();
        this.success = false;
        log.error("【{}】[{}] {} {}: {}", this.code, traceId, requestMethod, requestUrl, this.msg);
    }

    public ServerException(String msg, Throwable e) {
        super(msg, e);
        buildContextInfo();
        this.code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
        this.stackInfo = getFullStackTrace(e);
        this.success = false;
        log.error("【{}】[{}] {} {}: {}", this.code, traceId, requestMethod, requestUrl, this.msg, e);
    }

    public ServerException(ErrorCode errorCode, Throwable e) {
        super(errorCode.getMsg(), e);
        buildContextInfo();
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.stackInfo = getFullStackTrace(e);
        this.success = false;
        log.error("【{}】[{}] {} {}: {}", this.code, traceId, requestMethod, requestUrl, this.msg, e);
    }

}