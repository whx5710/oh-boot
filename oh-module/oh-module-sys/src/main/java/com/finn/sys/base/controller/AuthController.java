package com.finn.sys.base.controller;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.Tools;
import com.finn.core.utils.Result;
import com.finn.framework.utils.annotations.Idempotent;
import com.finn.framework.utils.annotations.RequestKeyParam;
import com.finn.support.service.AuthService;
import com.finn.support.service.CaptchaService;
import com.finn.support.vo.AccountLoginVO;
import com.finn.support.vo.CaptchaVO;
import com.finn.support.vo.MobileLoginVO;
import com.finn.support.vo.TokenVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
public class AuthController {
    private final CaptchaService captchaService;
    private final AuthService authService;

    public AuthController(CaptchaService captchaService, AuthService authService) {
        this.captchaService = captchaService;
        this.authService = authService;
    }

    @GetMapping("captcha")
    @Operation(summary = "验证码")
    public Result<CaptchaVO> captcha() {
        CaptchaVO captchaVO = captchaService.generate();
        return Result.ok(captchaVO);
    }

    @PostMapping("login")
    @Operation(summary = "账号密码登录")
    @Idempotent(keyPrefix = "auth:account", limit = true, message = "登录请求重复操作！") // 限制1秒内只能请求一次
    public Result<TokenVO> login(@RequestBody AccountLoginVO login) {
        TokenVO token = authService.loginByAccount(login);
        return Result.ok(token);
    }

    /**
     * 通过用户名、密码和密钥登录
     * 为了安全使用 @Idempotent 和 @RequestKeyParam 注解防止重复请求，频繁请求
     * @param userName 用户名
     * @param password base64编码的密码
     * @param userKey  用户密钥
     * @return token
     */
    @RequestMapping(value = "/loginByKey", method = {RequestMethod.GET,RequestMethod.POST})
    @Operation(summary = "账号密码登录(无验证码，适用于外部对接)")
    @Parameters({@Parameter(name="userName", description = "用户名"), @Parameter(name="password", description = "base64编码的密码"),
            @Parameter(name="userKey", description = "用户密钥")})
    @Idempotent(keyPrefix = "auth:key", timeout = 5, limit = true, message = "登录请求过于频繁，请稍候再操作！") // 同一个账号限制5秒内只能请求1次
    public Result<TokenVO> loginByKey(@RequestKeyParam @RequestParam String userName, @RequestParam String password,
                                      @RequestParam String userKey) {
        AssertUtils.isBlank(password, "密码不能为空！");
        AccountLoginVO login = new AccountLoginVO();
        login.setUsername(userName);
        try {
            password = Tools.base64Decode(password);
        }catch (IllegalArgumentException e){
            throw new ServerException("请检查密码参数是否合法！");
        }
        login.setPassword(password);
        login.setUserKey(userKey);
        TokenVO token = authService.loginByUserKey(login);
        return Result.ok(token);
    }

    @PostMapping("mobile")
    @Operation(summary = "手机号登录")
    public Result<TokenVO> mobile(@RequestBody MobileLoginVO login) {
        TokenVO token = authService.loginByMobile(login);
        return Result.ok(token);
    }

    @PostMapping("refreshToken")
    @Operation(summary = "刷新token")
    public Result<TokenVO> refreshToken(@RequestParam String refreshToken, HttpServletRequest request) {
        AssertUtils.isBlank(refreshToken, "刷新token");
        return Result.ok(authService.refreshToken(refreshToken, request));
    }

    @PostMapping("logout")
    @Operation(summary = "退出")
    public Result<String> logout(HttpServletRequest request, @RequestParam(required = false) String refreshToken) {
        authService.logout(Tools.getAccessToken(request), refreshToken);
        return Result.ok();
    }
}
