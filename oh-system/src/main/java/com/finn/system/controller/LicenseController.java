package com.finn.system.controller;

import com.finn.framework.entity.Result;
import com.finn.framework.license.LicenseManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @PostMapping("/install")
    public Result<String> install(@RequestBody String licenseJson) {
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
