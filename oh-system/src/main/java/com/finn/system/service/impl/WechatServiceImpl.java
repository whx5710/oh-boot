package com.finn.system.service.impl;

import com.finn.framework.security.wechat.WechatService;
import com.finn.framework.utils.AssertUtils;
import com.finn.framework.utils.HttpUtil;
import com.finn.framework.utils.Tools;
import com.finn.system.config.WechatProperties;
import com.finn.system.entity.UserEntity;
import com.finn.system.service.UserService;
import com.finn.system.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 微信登录
 */
@Service
public class WechatServiceImpl implements WechatService {

    private final WechatProperties wechatProperties;

    private final UserService userService;

    public WechatServiceImpl(WechatProperties wechatProperties, UserService userService){
        this.wechatProperties = wechatProperties;
        this.userService = userService;
    }

    @Override
    public String code2Session(String code) {
        String url = String.format("%s/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                wechatProperties.getApi(), wechatProperties.getApi(), wechatProperties.getSecret(), code);
        return HttpUtil.get(url);
    }

    @Override
    public UserDetails findOrCreateByOpenid(String openId) {
        AssertUtils.isBlank(openId, "第三方ID");
        String userType = "1";
        UserEntity user = userService.getByOpenId(openId, userType);
        if(user == null){
            // 如果用户不存在，则新增
            UserVO userVO = new UserVO();
            userVO.setOpenId(openId);
            userVO.setUsername(openId);
            userVO.setRealName("微信用户");
            userVO.setUserType(userType);
            userVO.setSuperAdmin(0);
            userVO.setStatus(1);
            userVO.setPassword(Tools.getRandom(8));
            userService.save(userVO);
            user = userService.getByOpenId(openId, userType);
        }
        return userService.getUserDetails(user);
    }

}
