package com.finn.core.excel;

import java.util.List;

/**
 * excel读取数据完成
 *
 * @param <T> the type parameter
 * @author 王小费
 * @since 2025-03-12
 */

public interface ExcelFinishCallBack<T> {

    /**
     * Do save batch.
     *
     * @param result the result
     */
    default void doSaveBatch(List<T> result) {
    }

}
