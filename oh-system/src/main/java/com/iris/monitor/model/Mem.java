package com.iris.monitor.model;

import cn.hutool.system.oshi.OshiUtil;
import com.iris.monitor.utils.ArityUtil;
import oshi.hardware.GlobalMemory;

/**
 * Mem Info
 *
 * @author 王小费
 */
public class Mem {

    /**
     * 内存总数(G)
     */
    private double total;

    /**
     * 已用内存(G)
     */
    private double used;

    /**
     * 剩余内存(G)
     */
    private double free;

    /**
     * 内存使用率
     */
    private double usage;

    public Mem() {
        GlobalMemory globalMemory = OshiUtil.getMemory();
        this.setTotal(ArityUtil.div(globalMemory.getTotal(), 1024 * 1024 * 1024, 2));
        this.setFree(ArityUtil.div(globalMemory.getAvailable(), 1024 * 1024 * 1024, 2));
        this.setUsed(ArityUtil.sub(this.getTotal(), this.getFree()));
        this.setUsage(ArityUtil.round(ArityUtil.div(this.getUsed(), this.getTotal(), 4) * 100, 2));
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getFree() {
        return free;
    }

    public void setFree(double free) {
        this.free = free;
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }
}
