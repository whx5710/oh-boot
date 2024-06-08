package com.iris.framework.security.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.iris.framework.common.constant.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.ObjectUtils;

/**
 * Token 工具类
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class TokenUtils {

    /**
     * 生成 AccessToken
     */
    public static String generator() {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 获取 AccessToken
     */
    public static String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (StrUtil.isBlank(accessToken)) {
            accessToken = request.getParameter("access_token");
        }
        if(!ObjectUtils.isEmpty(accessToken) && accessToken.startsWith(Constant.TOKEN_PREFIX)){
            accessToken = accessToken.substring(Constant.TOKEN_PREFIX.length());
        }
        return accessToken;
    }
}
