package com.iris.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import com.iris.framework.common.utils.Result;
import com.iris.framework.security.utils.TokenUtils;
import com.iris.system.service.SysAuthService;
import com.iris.system.service.SysCaptchaService;
import com.iris.system.vo.SysAccountLoginVO;
import com.iris.system.vo.SysCaptchaVO;
import com.iris.system.vo.SysMobileLoginVO;
import com.iris.system.vo.SysTokenVO;
import org.springframework.web.bind.annotation.*;

/**
 * 认证管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sys/auth")
@Tag(name = "认证管理")
public class SysAuthController {
    private final SysCaptchaService sysCaptchaService;
    private final SysAuthService sysAuthService;

    public SysAuthController(SysCaptchaService sysCaptchaService, SysAuthService sysAuthService) {
        this.sysCaptchaService = sysCaptchaService;
        this.sysAuthService = sysAuthService;
    }

    @GetMapping("captcha")
    @Operation(summary = "验证码")
    public Result<SysCaptchaVO> captcha() {
        SysCaptchaVO captchaVO = sysCaptchaService.generate();

        return Result.ok(captchaVO);
    }

    @PostMapping("login")
    @Operation(summary = "账号密码登录")
    public Result<SysTokenVO> login(@RequestBody SysAccountLoginVO login) {
        SysTokenVO token = sysAuthService.loginByAccount(login);

        return Result.ok(token);
    }

    @PostMapping("send/code")
    @Operation(summary = "发送短信验证码")
    public Result<String> sendCode(String mobile) {
        boolean flag = sysAuthService.sendCode(mobile);
        if (!flag) {
            return Result.error("短信发送失败！");
        }

        return Result.ok();
    }

    @PostMapping("mobile")
    @Operation(summary = "手机号登录")
    public Result<SysTokenVO> mobile(@RequestBody SysMobileLoginVO login) {
        SysTokenVO token = sysAuthService.loginByMobile(login);

        return Result.ok(token);
    }

    @PostMapping("logout")
    @Operation(summary = "退出")
    public Result<String> logout(HttpServletRequest request) {
        sysAuthService.logout(TokenUtils.getAccessToken(request));

        return Result.ok();
    }
}
