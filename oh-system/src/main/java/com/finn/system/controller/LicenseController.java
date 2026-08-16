package com.finn.system.controller;

import com.finn.framework.aop.annotations.Idempotent;
import com.finn.common.entity.Result;
import com.finn.framework.aop.annotations.RequestKeyParam;
import com.finn.license.LicenseManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * license授权相关
 * 获取机器码、授权信息以及授权证书安装
 */
@RestController
@RequestMapping("/license")
public class LicenseController {

    private final LicenseManager licenseManager;

    public LicenseController(LicenseManager licenseManager) {
        this.licenseManager = licenseManager;
    }

    /**
     * 获取当前服务器机器码，提供给授权方
     */
    @GetMapping("/machine-code")
    public Result<String> getMachineCode() {
        return Result.ok(licenseManager.getMachineCode());
    }

    /**
     * 上传 License 文件，安装后实时生效
     */
    @Idempotent(keyPrefix = "license", message = "请求过于频繁，请稍后再试", timeout = 10, limit = true)
    @PostMapping("/install")
    public Result<String> install(@RequestBody @RequestKeyParam String licenseJson) {
        licenseManager.install(licenseJson);
        return Result.ok("安装成功");
    }

    /**
     * 查看 License 状态
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> info() {
        return Result.ok(licenseManager.getLicenseInfo());
    }
}
