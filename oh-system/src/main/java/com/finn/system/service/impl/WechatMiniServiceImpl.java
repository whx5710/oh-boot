package com.finn.system.service.impl;

import com.finn.framework.entity.HashDto;
import com.finn.framework.security.user.UserDetail;
import com.finn.framework.security.wechat.WechatMiniService;
import com.finn.framework.utils.AssertUtils;
import com.finn.framework.utils.HttpUtil;
import com.finn.framework.utils.JsonUtils;
import com.finn.framework.utils.Tools;
import com.finn.system.config.WechatProperties;
import com.finn.system.entity.OpenUserEntity;
import com.finn.system.service.OpenUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 微信登录
 */
@Service
public class WechatMiniServiceImpl implements WechatMiniService {

    private final WechatProperties wechatProperties;

    private final OpenUserService openUserService;

    public WechatMiniServiceImpl(WechatProperties wechatProperties, OpenUserService openUserService){
        this.wechatProperties = wechatProperties;
        this.openUserService = openUserService;
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
        OpenUserEntity user = openUserService.getByOpenId(openId, userType);
        if(user == null){
            // 如果用户不存在，则新增
            OpenUserEntity userVO = new OpenUserEntity();
            userVO.setOpenId(openId);
            userVO.setUserName(openId);
            userVO.setRealName("微信用户");
            userVO.setUserType(userType);
            userVO.setStatus(1);
            userVO.setSecretKey(Tools.getRandom(8));
            openUserService.save(userVO);
            user = openUserService.getByOpenId(openId, userType);
        }
        // 转换成UserDetail对象
        UserDetail userDetail = new UserDetail();
        userDetail.setId(user.getId());
        userDetail.setUsername(user.getUserName());
        userDetail.setOpenId(user.getOpenId());
        userDetail.setUserType(user.getUserType());
        userDetail.setStatus(user.getStatus());
        userDetail.setRealName(user.getRealName());
        userDetail.setEmail(user.getEmail());
        userDetail.setMobile(user.getMobile());
        userDetail.setAvatar(user.getAvatar());
        userDetail.setGender(user.getGender());
        Set<String> permsSet = new HashSet<>();
        userDetail.setAuthoritySet(permsSet);
        return userDetail;
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

        String paramJson = JsonUtils.toJsonString(req);
        HashMap<String, String> headers = new HashMap<>();
        // 微信接口必须有Content-Length
        headers.put("Content-Length", paramJson.getBytes(StandardCharsets.UTF_8).length + "");
        String jsonStr = HttpUtil.post(url, req, headers);
        System.out.println("请求返回：" + jsonStr);
        HashDto result = JsonUtils.parseObject(jsonStr, HashDto.class);
        if (result.getInt("errcode") == 0) {
            HashDto phoneInfo = result.getDto("phone_info");
            return  phoneInfo.getStr("phoneNumber"); // 明文手机号
        }
        return "";

    }

    /**
     * 获取 access_token,是否需要缓存
     * @return
     */
    private String getAccessToken() {
        String url = String.format(wechatProperties.getApi() + "/cgi-bin/token?grant_type=client_credential" +
                "&appid=%s&secret=%s", wechatProperties.getAppId(), wechatProperties.getSecret()
        );
        String result = HttpUtil.get(url);
        System.out.println("获取微信小程序token " + result);
        HashDto hashDto = JsonUtils.parseObject(result, HashDto.class);
        return hashDto.getStr("access_token");
    }

}
