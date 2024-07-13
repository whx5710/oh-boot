package com.iris.framework.common.exception;

/**
 * 错误编码
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public enum ErrorCode {
    UNAUTHORIZED(401, "还未授权，不能访问"),
    FORBIDDEN(403, "没有权限，禁止访问"),
    NOT_FOUND(404, "未找到访问资源"),
    INTERNAL_SERVER_ERROR(500, "服务器异常，请稍后再试"),
    MISSING_PARAMETER_ERROR(500, "请求参数异常，请检查");


    private final int code;
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
