package com.iris.generator.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class PageResult<T> implements Serializable {
    // 总记录数
    private int total;

    // 列表数据
    private List<T> list;

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public PageResult(List<T> list, long total) {
        this.list = list;
        this.total = (int) total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}