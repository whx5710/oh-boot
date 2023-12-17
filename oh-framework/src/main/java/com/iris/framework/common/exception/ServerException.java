package com.iris.framework.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.Objects;

/**
 * 自定义异常
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class ServerException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Logger log = LoggerFactory.getLogger(ServerException.class);

    /**
     * 错误代码
     */
    private int code;
    /**
     * 提示消息
     */
    private String msg;

    /**
     * 堆栈信息
     * 一般不返回到前端，供后台查看错误原因
     */
    private String stackInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerException that = (ServerException) o;
        return code == that.code && Objects.equals(msg, that.msg) && Objects.equals(stackInfo, that.stackInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, stackInfo);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    /**
     *
     * @param msg 错误信息提示
     */
    public ServerException(String msg) {
        super(msg);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
        this.stackInfo = msg;
        log.error("【{}】{}", code, msg);
    }

    /**
     *
     * @param msg 提示给前端信息
     * @param stackInfo 堆栈信息
     */
    public ServerException(String msg, String stackInfo) {
        super(msg);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
        this.stackInfo = stackInfo;
        log.error("【{}】{} 异常提示:{}", code, msg, stackInfo);
    }

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.stackInfo = errorCode.getMsg();
        log.error("【{}】{}", this.code, this.msg);
    }

    public ServerException(String msg, Throwable e) {
        super(msg, e);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
        this.stackInfo = msg;
        log.error("【{}】{}", this.code, this.msg);
    }

}