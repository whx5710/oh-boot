package com.finn.core.entity;

/**
 * 异常堆栈信息
 * @param <T>
 */
public class ResultError<T> extends Result<T>{

    /**
     * 堆栈信息
     */
    private String stackInfo;

    public String getStackInfo() {
        return stackInfo;
    }

    public void setStackInfo(String stackInfo) {
        this.stackInfo = stackInfo;
    }
}
