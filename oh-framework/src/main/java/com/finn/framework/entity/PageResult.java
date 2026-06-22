package com.finn.framework.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * list和total
 * @author 王小费 whx5710@qq.com
 *
 */
public class PageResult<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<T> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 分页
     * @param list   列表数据
     * @param total  总记录数
     */
    public PageResult(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }
}