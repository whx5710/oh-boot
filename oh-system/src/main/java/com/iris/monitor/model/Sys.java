package com.iris.monitor.model;

import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.OshiUtil;

/**
 * System Info
 *
 * @author 王小费
 */
public class Sys {

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;

    /**
     * 系统版本
     */
    private String osVersion;


    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    public Sys() {
        this.setOsName(SystemUtil.getOsInfo().getName());
        this.setOsArch(SystemUtil.getOsInfo().getArch());
        this.setOsVersion(SystemUtil.getOsInfo().getVersion());
        this.setComputerName(OshiUtil.getOs().getNetworkParams().getHostName());
        this.setComputerIp(SystemUtil.getHostInfo().getAddress());
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerIp() {
        return computerIp;
    }

    public void setComputerIp(String computerIp) {
        this.computerIp = computerIp;
    }
}
