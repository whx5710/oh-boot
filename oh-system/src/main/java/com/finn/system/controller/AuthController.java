package com.finn.system.controller;

import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.framework.utils.AssertUtils;
import com.finn.framework.utils.Tools;
import com.finn.framework.entity.Result;
import com.finn.framework.aop.annotations.Idempotent;
import com.finn.framework.aop.annotations.RequestKeyParam;
import com.finn.system.service.AuthService;
import com.finn.system.service.CaptchaService;
import com.finn.system.service.UserRoleService;
import com.finn.system.vo.AccountLoginVO;
import com.finn.system.vo.CaptchaVO;
import com.finn.system.vo.MobileLoginVO;
import com.finn.system.vo.TokenVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 认证管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("/sys/auth")
public class AuthController {
    private final CaptchaService captchaService;
    private final AuthService authService;
    private final UserRoleService userRoleService;

    public AuthController(CaptchaService captchaService, AuthService authService,
                          UserRoleService userRoleService) {
        this.captchaService = captchaService;
        this.authService = authService;
        this.userRoleService = userRoleService;
    }

    /**
     * 验证码
     * @return 验证码信息
     */
    @GetMapping("/captcha")
    public Result<CaptchaVO> captcha(@RequestParam String platformKey) {
        return Result.ok(captchaService.generateImg(platformKey));
    }

    /**
     * 账号密码登录
     * @param login 登录信息
     * @return token信息
     */
    @PostMapping("/login")
    @Idempotent(keyPrefix = "auth:account", limit = true, message = "登录请求重复操作！") // 限制1秒内只能请求一次
    public Result<TokenVO> login(@RequestBody AccountLoginVO login) {
        return Result.ok(authService.loginByAccount(login));
    }

    /**
     * 微信登录
     *用户点击微信登录
     *     ↓
     * 小程序调用 wx.login() 获取 code
     *     ↓
     * 前端将 code 发送到后端
     *     ↓
     * 后端用 code + appid + secret 请求微信接口获取 openid/session_key
     *     ↓
     * 后端根据 openid 查找或创建用户
     *     ↓
     * 后端生成 JWT Token 返回给前端
     *     ↓
     * 前端存储 token，后续请求携带 Authorization: Bearer <token>
     * @param code 微信code
     * @return token信息
     */
    @RequestMapping(value = "/wechat-login", method = {RequestMethod.GET,RequestMethod.POST})
    public Result<TokenVO> wechatLogin(@RequestParam String code) {
        return Result.ok(authService.wechatLogin(code));
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
        return Result.ok(authService.loginByUserKey(login));
    }

    /**
     * 手机号登录
     * @param login
     * @return
     */
    @PostMapping("/mobile")
    public Result<TokenVO> mobile(@RequestBody MobileLoginVO login) {
        return Result.ok(authService.loginByMobile(login));
    }

    /**
     * 用户权限标识
     * @return
     */
    @GetMapping("/authority")
    public Result<Set<String>> authority() {
        UserDetail user = SecurityUser.getUser();
        Set<String> set = userRoleService.getUserAuthority(user);
        return Result.ok(set);
    }

    /**
     * 刷新token
     * @param refreshToken
     * @param request
     * @return
     */
    @PostMapping("/refresh")
    public Result<TokenVO> refresh(@RequestParam String refreshToken, HttpServletRequest request) {
        AssertUtils.isBlank(refreshToken, "刷新token");
        return Result.ok(authService.refreshToken(refreshToken, request));
    }

    /**
     * 退出
     * @param request
     * @param refreshToken
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request, @RequestParam(required = false) String refreshToken) {
        authService.logout(Tools.getAccessToken(request), refreshToken);
        return Result.ok();
    }
}
