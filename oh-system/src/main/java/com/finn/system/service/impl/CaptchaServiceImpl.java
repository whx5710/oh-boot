package com.finn.system.service.impl;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.common.utils.Tools;
import com.finn.common.utils.VerifyUtil;
import com.finn.system.enums.ParamsEnum;
import com.finn.system.service.CaptchaService;
import com.finn.system.service.ParamsService;
import com.finn.system.vo.CaptchaVO;
import org.springframework.stereotype.Service;

import java.awt.*;

/**
 * 验证码
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    private final RedisCache redisCache;
    private final ParamsService paramsService;

    public CaptchaServiceImpl(RedisCache redisCache, ParamsService paramsService) {
        this.redisCache = redisCache;
        this.paramsService = paramsService;
    }

    /**
     * 自定义工具类生成验证码
     * @return
     */
    @Override
    public CaptchaVO generateImg(String platformKey) {
        // 判断是否开启验证码
        if (!enableCaptcha(platformKey)) {
            return new CaptchaVO();
        }
        CaptchaVO captchaVO = new CaptchaVO();
        int width = 150; // 验证码宽度
        int height = 40; // 验证码高度
        int captchaLen = paramsService.getDefaultInt(ParamsEnum.CAPTCHA_LENGTH.getCode());
        captchaLen = captchaLen==-1?5:captchaLen; // 长度
        Object[] objects = VerifyUtil.newBuilder()
                .setWidth(width)   //设置图片的宽度
                .setHeight(height)   //设置图片的高度
                .setSize(captchaLen)      //设置字符的个数
                .setLines(10)    //设置干扰线的条数
                .setFontSize(25) //设置字体的大小
                .setTilt(true)   //设置是否需要倾斜
                .setBackgroundColor(Color.WHITE) //设置验证码的背景颜色
                .build()         //构建VerifyUtil项目
                .createCodeAndBase64Image();  // 生成base64图片
        String code = (String) objects[0];// 获取验证码
        // 生成验证码key
        String key = Tools.generator();
        String redisKey = RedisKeys.getCaptchaKey(key);
        // 保存到缓存 有效30秒
        redisCache.set(redisKey, code, 30);
        String base64Image = (String) objects[1];
        // 封装返回数据
        captchaVO.setKey(key);
        captchaVO.setEnabled(true);
        captchaVO.setImage(base64Image);
        return captchaVO;
    }

    /**
     * 验证码效验
     * @param platformKey 平台key 电脑浏览器端：PC 移动app端：APP
     * @param key  key
     * @param code 验证码
     * @return true：成功  false：失败
     */
    @Override
    public boolean validate(String platformKey, String key, String code) {
        // 如果关闭了验证码，则直接效验通过
        if (!enableCaptcha(platformKey)) {
            return true;
        }
        if ((key == null || key.isEmpty()) || (code == null || code.isEmpty())) {
            return false;
        }
        // 获取验证码
        String captcha = getCache(key);
        // 效验成功
        return code.equalsIgnoreCase(captcha);
    }

    private String getCache(String key) {
        key = RedisKeys.getCaptchaKey(key);
        String captcha = (String) redisCache.get(key);
        // 删除验证码
        if (captcha != null) {
            redisCache.delete(key);
        }
        return captcha;
    }

    /**
     * 是否开启登录验证码
     *
     * @return true：开启  false：关闭
     */
    private boolean enableCaptcha(String platformKey) {
        if(platformKey == null || platformKey.isEmpty()){
            platformKey = "PC";
        }
        platformKey = platformKey.toUpperCase();
        if(platformKey.equals(ParamsEnum.PC.name())){
            return paramsService.getBoolean(ParamsEnum.PC.getCode());
        }else if(platformKey.equals(ParamsEnum.APP.name())){
            return paramsService.getBoolean(ParamsEnum.APP.getCode());
        }else{
            return true;
        }
    }
}
