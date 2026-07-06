package com.finn.system.controller;

import com.finn.framework.entity.Result;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.wechat.WechatMiniService;
import com.finn.framework.utils.AssertUtils;
import com.finn.system.entity.OpenUserEntity;
import com.finn.system.mapper.OpenUserMapper;
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
    private final WechatMiniService wechatMiniService;
    private final OpenUserMapper openUserMapper;

    public WechatController(WechatMiniService wechatMiniService, OpenUserMapper openUserMapper){
        this.wechatMiniService = wechatMiniService;
        this.openUserMapper = openUserMapper;
    }

    /**
     * 解码手机号和绑定
     * @param params
     * @return
     */
    @PostMapping("/bindPhone")
    public Result<String> login(@RequestBody Map<String, String> params) {
        // 解密手机号
        String phone = wechatMiniService.getPhoneNumber(params);
        // 更新手机号
        if(params.containsKey("bind") && params.get("bind") != null
                && params.get("bind").equals("1")){
            Long userId = SecurityUser.getUserId();
            if(userId == null){
                throw new SecurityException("请先登录");
            }
            OpenUserEntity user = openUserMapper.findById(userId, OpenUserEntity.class);
            if(user == null){
                throw new SecurityException("未找到用户信息");
            }
            user.setMobile(phone);
            openUserMapper.updateById(user);
        }
        return Result.ok(phone);
    }

    /**
     * 修改用户信息（昵称、头像、手机号，性别）
     * @param vo 用户
     * @return 提示信息
     */
    @PostMapping("/update")
    public Result<String> update(@RequestBody OpenUserEntity vo) {
        AssertUtils.isNull(vo.getId(), "用户ID");
        OpenUserEntity user = openUserMapper.findById(vo.getId(), OpenUserEntity.class);
        if(user == null || user.getId() == null){
            throw new ServerException("未找到该用户");
        }
        if(!user.getUserType().equals("1")){
            throw new ServerException("该用户不是微信用户");
        }
        user.setAvatar(vo.getAvatar());
        user.setMobile(vo.getMobile());
        user.setRealName(vo.getRealName());
        user.setGender(vo.getGender());
        openUserMapper.updateById(user);
        return Result.ok("修改成功");
    }
}
