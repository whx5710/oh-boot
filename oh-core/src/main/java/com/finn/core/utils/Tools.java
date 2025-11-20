package com.finn.core.utils;

import com.finn.core.constant.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.ObjectUtils;

import java.util.*;
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
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-","");
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
     * @return str 最小返回4个长度随机数
     */
    public static String getRandom(int length){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        // 随机取一位大小写字母、字符和数字，长度4位
        int number = random.nextInt(LOW_LETTER.length());
        sb.append(LOW_LETTER.charAt(number));

        number = random.nextInt(CAPITAL.length());
        sb.append(CAPITAL.charAt(number));

        number = random.nextInt(NUM.length());
        sb.append(NUM.charAt(number));

        number = random.nextInt(CHAR.length());
        sb.append(CHAR.charAt(number));

        int l = length - 4;
        for(int i = 0; i < l; i++){
            number = random.nextInt((LOW_LETTER + CAPITAL + NUM + CHAR).length());
            sb.append((LOW_LETTER + CAPITAL + NUM + CHAR).charAt(number));
        }
        String msg = sb.toString();
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
