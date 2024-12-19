package com.iris.sys.monitor.model;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.system.SystemUtil;
import com.iris.sys.monitor.utils.ArityUtil;

import java.util.Date;
import java.util.List;

/**
 * Jvm Info
 *
 * @author 王小费
 */
public class Jvm {

    /**
     * JVM 最大可用内存总数(G)
     */
    private double max;

    /**
     * JVM 占用的内存总数(M)
     */
    private double total;

    /**
     * JVM 已用内存(M)
     */
    private double used;

    /**
     * JVM 空闲内存(M)
     */
    private double free;

    /**
     * JVM 内存使用率
     */
    private double usage;

    /**
     * JVM 名称
     */
    private String name;

    /**
     * Java Version
     */
    private String version;

    /**
     * JavaVM Vendor
     */
    private String vendor;

    /**
     * JDK 路径
     */
    private String home;

    /**
     * JarDir
     */
    private String userDir;

    /**
     * JVM 启动时间
     */
    private String startTime;

    /**
     * JVM 运行时间
     */
    private String runTime;

    /**
     * JVM InputArguments
     */
    private List<String> inputArguments;

    public Jvm() {
        this.setMax(ArityUtil.div(SystemUtil.getMaxMemory(), 1024 * 1024 * 1024, 2));
        this.setTotal(ArityUtil.div(SystemUtil.getTotalMemory(), 1024 * 1024 * 1024, 2));
        this.setFree(ArityUtil.div(SystemUtil.getFreeMemory(), 1024 * 1024 * 1024, 2));
        this.setUsed(ArityUtil.round(this.getTotal() - this.getFree(), 2));
        this.setUsage(ArityUtil.div(this.getUsed(), this.getTotal(), 4) * 100);
        this.setName(SystemUtil.getRuntimeMXBean().getVmName());
        this.setVersion(SystemUtil.getJavaInfo().getVersion());
        this.setVendor(SystemUtil.getJavaInfo().getVendor());
        this.setHome(SystemUtil.getJavaRuntimeInfo().getHomeDir());
        this.setUserDir(SystemUtil.getUserInfo().getCurrentDir());
        Date startTime = new Date(SystemUtil.getRuntimeMXBean().getStartTime());
        this.setStartTime(DateUtil.formatDateTime(startTime));
        this.setRunTime(DateUtil.formatBetween(startTime, new Date(), BetweenFormatter.Level.SECOND));
        this.setInputArguments(SystemUtil.getRuntimeMXBean().getInputArguments());
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public List<String> getInputArguments() {
        return inputArguments;
    }

    public void setInputArguments(List<String> inputArguments) {
        this.inputArguments = inputArguments;
    }
}
