package com.iris.monitor.model;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;

/**
 * Cpu Info
 *
 * @author 王小费
 */
public class Cpu {

    /**
     * 设置等待时间，单位毫秒
     */
    private static final Long JOSHI_WAIT_SECOND = 360L;

    /**
     * CPU型号
     */
    private String cpuModel;

    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public Cpu() {
        // 获取CPU相关信息,间隔1秒
        CpuInfo cpuInfo = OshiUtil.getCpuInfo(JOSHI_WAIT_SECOND);
        this.setCpuModel(cpuInfo.getCpuModel().split("\n")[0]);
        this.setCpuNum(cpuInfo.getCpuNum());
        this.setTotal(cpuInfo.getToTal());
        this.setSys(cpuInfo.getSys());
        this.setUsed(cpuInfo.getUser());
        this.setWait(cpuInfo.getWait());
        this.setFree(cpuInfo.getFree());
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public int getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSys() {
        return sys;
    }

    public void setSys(double sys) {
        this.sys = sys;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getWait() {
        return wait;
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getFree() {
        return free;
    }

    public void setFree(double free) {
        this.free = free;
    }
}
