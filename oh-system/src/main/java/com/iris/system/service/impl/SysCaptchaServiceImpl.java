package com.iris.system.service.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import com.iris.system.enums.SysParamsEnum;
import com.iris.system.service.SysCaptchaService;
import com.iris.system.service.SysParamsService;
import com.iris.system.vo.SysCaptchaVO;
import org.springframework.stereotype.Service;

/**
 * 验证码
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysCaptchaServiceImpl implements SysCaptchaService {
    private final RedisCache redisCache;
    private final SysParamsService sysParamsService;

    public SysCaptchaServiceImpl(RedisCache redisCache, SysParamsService sysParamsService) {
        this.redisCache = redisCache;
        this.sysParamsService = sysParamsService;
    }

    @Override
    public SysCaptchaVO generate() {
        // 判断是否开启验证码
        if (!isCaptchaEnabled()) {
            return new SysCaptchaVO();
        }
        SysCaptchaVO captchaVO = new SysCaptchaVO();
        // 生成验证码key
        String key = UUID.randomUUID().toString();
        String redisKey = RedisKeys.getCaptchaKey(key);
        int width = 150; // 验证码宽度
        int height = 40; // 验证码高度
        // 验证码类型
        int captchaType = sysParamsService.getDefaultInt(SysParamsEnum.CAPTCHA_TYPE.name());
        captchaType = captchaType==-1?4:captchaType;
        int captchaLen = sysParamsService.getDefaultInt(SysParamsEnum.CAPTCHA_LENGTH.name());
        captchaLen = captchaLen==-1?5:captchaLen; // 长度
        AbstractCaptcha captcha = switch (captchaType) {
            case 2 -> CaptchaUtil.createGifCaptcha(width, height, captchaLen); // gif类型
            case 3 -> CaptchaUtil.createCircleCaptcha(width, height, captchaLen, 20); // 圆圈干扰
            case 4 -> CaptchaUtil.createLineCaptcha(width, height, captchaLen, 20); // 线条干扰
            default -> CaptchaUtil.createShearCaptcha(width, height, captchaLen, 2); // 图形干扰
        };
        String image = captcha.getImageBase64Data();
        captchaVO.setImage(image);
        // 保存到缓存\
        redisCache.set(redisKey, captcha.getCode(), 300);
        // 封装返回数据
        captchaVO.setKey(key);
        captchaVO.setEnabled(true);
        return captchaVO;
    }

    @Override
    public boolean validate(String key, String code) {
        // 如果关闭了验证码，则直接效验通过
        if (!isCaptchaEnabled()) {
            return true;
        }

        if (StrUtil.isBlank(key) || StrUtil.isBlank(code)) {
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
    private boolean isCaptchaEnabled() {
        return sysParamsService.getBoolean(SysParamsEnum.LOGIN_CAPTCHA.name());
    }
}
