package com.finn.framework.entity;

import com.finn.framework.common.enums.ErrorCode;

import java.util.List;

/**
 * 响应数据
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class Result<T> {
    /**
     * 编码 200表示成功，其他值表示失败
     */
    private int code = 200;

    /**
     * 请求是否成功
     */
    private Boolean success = true;

    /**
     * 消息内容
     */
    private String msg = "success";

    /**
     * 响应数据
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    /**
     * 返回结果集
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    /**
     * 分页数据
     * @param list 分页list
     * @param total 总数
     * @return result
     * @param <T> t
     */
    public static <T> Result<PageResult<T>> ok(List<T> list, long total) {
        PageResult<T> pageResult = new PageResult<>(list, total);

        Result<PageResult<T>> result = new Result<>();
        result.setData(pageResult);
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> error() {
        return error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMsg());
    }

    public static <T> Result<T> error(String msg) {
        return error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    /**
     *
     * @param errorCode 错误编码和信息
     * @param stackInfo 堆栈信息
     * @return
     * @param <T>
     */
    public static <T> Result<T> error(ErrorCode errorCode, String stackInfo) {
        return error(errorCode.getCode(), errorCode.getMsg(), stackInfo);
    }

    /**
     *
     * @param code 错误编码
     * @param msg 信息
     * @return
     * @param <T>
     */
    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    /**
     * 构建异常信息
     * @param code 异常编码
     * @param msg 错误信息
     * @param stackInfo 堆栈信息
     * @return re
     * @param <T>
     */
    public static <T> ResultError<T> error(int code, String msg, String stackInfo) {
        ResultError<T> result = new ResultError<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        result.setStackInfo(stackInfo);
        return result;
    }

    /**
     *
     * @param e 异常信息
     * @return
     * @param <T>
     */
    public static <T> ResultError<T> error(RuntimeException e) {
        ResultError<T> result = new ResultError<>();
        result.setCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        result.setMsg(ErrorCode.INTERNAL_SERVER_ERROR.getMsg());
        result.setSuccess(false);
        result.setStackInfo(e.getMessage());
        return result;
    }
}
