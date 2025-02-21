package com.finn.core.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.finn.core.constant.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统常用公共工具
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class Tools {

    public static final String LOW_LETTER = "abcdefghijklmnopqrstuvwxyz";

    public static final String CAPITAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String NUM = "1234567890";

    public static final String CHAR = "~!@#$%^&*()_+";

    private static final Pattern humpPattern = Pattern.compile("[A-Z]");

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

    /**
     * 生成length个长度随机字符串
     * @param length 长度
     * @return str 最小返回5个长度随机数
     */
    public static String getRandom(int length){
        String a = RandomUtil.randomString(LOW_LETTER, 1);
        String b = RandomUtil.randomString(CAPITAL, 1);
        String c = RandomUtil.randomString(NUM, 1);
        String d = RandomUtil.randomString(CHAR, 1);
        String msg = a + b + c + d + RandomUtil.randomString(LOW_LETTER + CAPITAL + NUM + CHAR, length - 4);
        String [] str = msg.split("");
        List<String> list = Arrays.asList(str);
        Collections.shuffle(list); // 随机排序
        StringBuilder stringBuilder = new StringBuilder(list.size());
        list.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }


    /**
     * 驼峰转下划线
     * @param str 原始
     * @return 下划线
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        String s = sb.toString();
        if(s.startsWith("_")){
            s = s.substring(1);
        }
        return s;
    }

}
