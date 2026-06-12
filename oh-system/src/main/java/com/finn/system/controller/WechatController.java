package com.finn.system.controller;

import com.finn.framework.entity.Result;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.wechat.WechatService;
import com.finn.system.entity.UserEntity;
import com.finn.system.mapper.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 认证管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("/wechat")
public class WechatController {
    private final WechatService wechatService;
    private final UserMapper userMapper;

    public WechatController(WechatService wechatService, UserMapper userMapper){
        this.wechatService = wechatService;
        this.userMapper = userMapper;
    }

    /**
     * 解码手机号和绑定
     * @param params
     * @return
     */
    @PostMapping("/bindPhone")
    public Result<String> login(@RequestBody Map<String, String> params) {
        // 解密手机号
        String phone = wechatService.getPhoneNumber(params);
        // 更新手机号
        if(params.containsKey("bind") && params.get("bind") != null
                && params.get("bind").equals("1")){
            Long userId = SecurityUser.getUserId();
            if(userId == null){
                throw new SecurityException("请先登录");
            }
            UserEntity user = userMapper.findById(userId, UserEntity.class);
            if(user == null){
                throw new SecurityException("未找到用户信息");
            }
            user.setMobile(phone);
            userMapper.updateById(user);
        }
        return Result.ok(phone);
    }
}
