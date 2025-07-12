package com.finn.core.aspect;

import java.io.Serializable;

/**
 * 函数式接口
 * @param <T>
 * @author 王小费
 * @since 2025-06-15
 */
@FunctionalInterface
public interface FuncUtils<T> extends Serializable {
    Object apply(T t);
}
