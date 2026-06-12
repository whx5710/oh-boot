package com.finn.system.service.impl;

import com.finn.framework.entity.HashDto;
import com.finn.framework.security.wechat.WechatService;
import com.finn.framework.utils.AssertUtils;
import com.finn.framework.utils.HttpUtil;
import com.finn.framework.utils.JsonUtils;
import com.finn.framework.utils.Tools;
import com.finn.system.config.WechatProperties;
import com.finn.system.entity.UserEntity;
import com.finn.system.service.UserService;
import com.finn.system.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
                wechatProperties.getApi(), wechatProperties.getAppId(), wechatProperties.getSecret(), code);
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

    @Override
    public String getPhoneNumber(Map<String, String> body) {
        String code = body.get("code");
        // 直接调用微信接口获取手机号
        String url = String.format(wechatProperties.getApi() + "/wxa/business/getuserphonenumber" +
                "?access_token=%s", getAccessToken()
        );
        Map<String, String> req = new HashMap<>();
        req.put("code", code);

        String jsonStr = HttpUtil.post(url, req);
        System.out.println("请求返回：" + jsonStr);

        HashDto result = JsonUtils.parseObject(jsonStr, HashDto.class);
        if (result.getInt("errcode") == 0) {
            HashDto phoneInfo = JsonUtils.parseObject(result.getStr("phone_info"), HashDto.class);
            return  phoneInfo.getStr("phoneNumber"); // 明文手机号
        }
        return "";

    }

    // 获取 access_token
    private String getAccessToken() {
        String url = String.format(wechatProperties.getApi() + "/cgi-bin/token?grant_type=client_credential" +
                "&appid=%s&secret=%s", wechatProperties.getAppId(), wechatProperties.getSecret()
        );
        String result = HttpUtil.get(url);
        HashDto hashDto = JsonUtils.parseObject(result, HashDto.class);
        return hashDto.getStr("access_token");
    }

}
