package com.iris.sys.monitor.controller;

import com.iris.core.utils.Result;
import com.iris.sys.monitor.model.*;
import com.iris.sys.monitor.vo.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 服务器监控
 *
 * @author 王小费
 */
@RestController
@RequestMapping("monitor/server")
@Tag(name="服务器资源监控")
public class ServerController {

    /**
     * 服务器相关信息
     */
    @Operation(summary = "服务器相关信息")
    @GetMapping("info")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Server> getServerInfo() {
        Server server = new Server();
        return Result.ok(server);
    }

    /**
     * CPU相关信息
     */
    @Operation(summary = "CPU相关信息")
    @GetMapping("cpu")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Cpu> getCpuInfo() {
        Cpu cpu = new Cpu();
        return Result.ok(cpu);
    }

    /**
     * 内存相关信息
     */
    @GetMapping("mem")
    @Operation(summary = "内存相关信息")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Mem> getMemInfo() {
        Mem mem = new Mem();
        return Result.ok(mem);
    }

    /**
     * JVM相关信息
     */
    @GetMapping("jvm")
    @Operation(summary = "JVM相关信息")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Jvm> getJvmInfo() {
        Jvm jvm = new Jvm();
        return Result.ok(jvm);
    }

    /**
     * 系统相关信息
     */
    @GetMapping("sys")
    @Operation(summary = "系统相关信息")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Sys> getSysInfo() {
        Sys sys = new Sys();
        return Result.ok(sys);
    }

    /**
     * 系统文件相关信息
     */
    @GetMapping("disk")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    @Operation(summary = "系统文件相关信息")
    public Result<List<Disk>> getSysFileInfo() {
        Server server = new Server(new Disk());
        return Result.ok(server.getDisks());
    }

}
