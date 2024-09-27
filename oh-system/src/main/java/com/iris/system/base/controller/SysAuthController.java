package com.iris.system.base.controller;

import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.framework.common.utils.IrisTools;
import com.iris.system.base.vo.SysAccountLoginVO;
import com.iris.system.base.vo.SysCaptchaVO;
import com.iris.system.base.vo.SysMobileLoginVO;
import com.iris.system.base.vo.SysTokenVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import com.iris.framework.common.utils.Result;
import com.iris.system.base.service.SysAuthService;
import com.iris.system.base.service.SysCaptchaService;
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

    /**
     * 通过用户名、密码和密钥登录
     * 为了安全，请在服务端调用该接口，防止直接打开链接，只支持post
     * @param userName 用户名
     * @param password base64编码的密码
     * @param userKey  用户密钥
     * @return token
     */
//    @RequestMapping(value = "/loginByKey", method = {RequestMethod.GET,RequestMethod.POST})
    @PostMapping("/loginByKey")
    @Operation(summary = "账号密码登录(无验证码，适用于外部对接)")
    @Parameters({@Parameter(name="userName", description = "用户名"), @Parameter(name="password", description = "base64编码的密码"),
            @Parameter(name="userKey", description = "用户密钥")})
    public Result<SysTokenVO> loginByKey(@RequestParam String userName, @RequestParam String password,
                                         @RequestParam String userKey) {
        AssertUtils.isBlank(password, "密码不能为空！");
        SysAccountLoginVO login = new SysAccountLoginVO();
        login.setUsername(userName);
        try {
            password = IrisTools.base64Decode(password);
        }catch (IllegalArgumentException e){
            throw new ServerException("请检查密码参数是否合法！");
        }
        login.setPassword(password);
        login.setUserKey(userKey);
        SysTokenVO token = sysAuthService.loginByUserKey(login);
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

    @PostMapping("refreshToken")
    @Operation(summary = "刷新token")
    public Result<SysTokenVO> refreshToken(@RequestParam String refreshToken, HttpServletRequest request) {
        AssertUtils.isBlank(refreshToken, "刷新token");
        return Result.ok(sysAuthService.refreshToken(refreshToken, request));
    }

    @PostMapping("logout")
    @Operation(summary = "退出")
    public Result<String> logout(HttpServletRequest request) {
        sysAuthService.logout(IrisTools.getAccessToken(request));
        return Result.ok();
    }
}
