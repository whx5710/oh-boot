package com.finn.support.vo;

import java.io.Serial;
import java.io.Serializable;

/**
 * 图片验证码
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class CaptchaVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    private String key;

    /**
     * 图片 base64 编码
     */
    private String image;

    /**
     * 是否开启验证码
     */
    private boolean enabled;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
