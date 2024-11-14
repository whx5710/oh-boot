package com.iris.core.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.iris.core.constant.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.ObjectUtils;

import java.util.Base64;

/**
 * 系统常用公共工具
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class IrisTools {

    // 参数1为终端ID，参数2为数据中心ID
    static Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    /**
     * Base64 编码
     * @param src 原字符串
     * @return 编码后的字符串
     */
    public static String base64Encode(String src) {
        byte[] encodeBytes = Base64.getEncoder().encode(src.getBytes());
        return new String(encodeBytes);
    }

    /**
     * base64 解码
     * @param src 待解码字符串
     * @return str
     */
    public static String base64Decode(String src) throws IllegalArgumentException{
        byte[] decodeBytes = Base64.getDecoder().decode(src.getBytes());
        return new String(decodeBytes);
    }

    /**
     * 生成随机字符串UUID
     */
    public static String generator() {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 生成雪花算法ID
     * @return
     */
    public static long snowFlakeId() {
        return snowflake.nextId();
    }

    /**
     * 获取 AccessToken
     */
    public static String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(Constant.AUTHORIZATION);
        if (accessToken == null || accessToken.isEmpty()) {
            accessToken = request.getParameter(Constant.ACCESS_TOKEN);
        }
        if(!ObjectUtils.isEmpty(accessToken) && accessToken.startsWith(Constant.TOKEN_PREFIX)){
            accessToken = accessToken.substring(Constant.TOKEN_PREFIX.length());
        }
        return accessToken;
    }

    /**
     * 补全自增的数据
     *
     * @param seq
     * @param length
     * @return
     */
    public static String getSequence(long seq, int length) {
        String str = String.valueOf(seq);
        int len = str.length();
        // 取决于业务规模
        if (len >= length) {
            return str;
        }
        int rest = length - len;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rest; i++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }
}
