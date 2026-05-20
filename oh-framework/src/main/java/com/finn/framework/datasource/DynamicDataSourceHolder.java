package com.finn.framework.datasource;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 数据源切换处理
 * DynamicDataSourceHolder类主要是设置当前线程的数据源名称
 * 移除数据源名称，以及获取当前数据源的名称，便于动态切换
 * <p>
 * 优化：使用栈结构支持嵌套数据源切换，解决内部方法切换数据源导致外层数据源失效的问题
 *
 * @author 王小费 whx5710@qq.com
 */
public class DynamicDataSourceHolder {

    /**
     * 保存动态数据源名称，使用栈结构支持嵌套切换
     */
    private static final ThreadLocal<Deque<String>> DYNAMIC_DATASOURCE_KEY = ThreadLocal.withInitial(ArrayDeque::new);

    /**
     * 设置/切换数据源，决定当前线程使用哪个数据源
     * 使用栈结构，支持嵌套切换
     *
     * @param key 数据源key
     */
    public static void pushDynamicDataSourceKey(String key) {
        DYNAMIC_DATASOURCE_KEY.get().push(key);
    }

    /**
     * 获取当前数据源名称（栈顶元素）
     *
     * @return 当前数据源key，如果没有设置则返回null
     */
    public static String getDynamicDataSourceKey() {
        return DYNAMIC_DATASOURCE_KEY.get().peek();
    }

    /**
     * 移除当前数据源（弹出栈顶元素）
     * 如果栈为空，则清理ThreadLocal避免内存泄漏
     */
    public static void popDynamicDataSourceKey() {
        Deque<String> deque = DYNAMIC_DATASOURCE_KEY.get();
        if (deque != null && !deque.isEmpty()) {
            deque.pop();
            // 如果栈为空，清理ThreadLocal
            if (deque.isEmpty()) {
                DYNAMIC_DATASOURCE_KEY.remove();
            }
        }
    }

    /**
     * 判断是否已设置数据源
     *
     * @return true 如果已设置数据源
     */
    public static boolean hasDynamicDataSourceKey() {
        return !DYNAMIC_DATASOURCE_KEY.get().isEmpty();
    }

    /**
     * 清空所有数据源设置（谨慎使用）
     */
    public static void clear() {
        DYNAMIC_DATASOURCE_KEY.remove();
    }

    /**
     * 获取当前栈的深度（用于调试）
     *
     * @return 栈深度
     */
    public static int getStackDepth() {
        return DYNAMIC_DATASOURCE_KEY.get().size();
    }
}
