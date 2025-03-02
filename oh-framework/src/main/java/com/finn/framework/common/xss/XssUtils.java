package com.finn.framework.common.xss;

import com.finn.core.utils.ReflectUtil;
import com.finn.framework.common.xss.http.HTMLFilter;


/**
 * XSS 过滤工具类
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class XssUtils {
    private static final ThreadLocal<HTMLFilter> HTML_FILTER = ThreadLocal.withInitial(() -> {
        HTMLFilter htmlFilter = new HTMLFilter();
        // 避免 " 被转成 &quot; 字符
        try {
            ReflectUtil.setValue(htmlFilter, "encodeQuotes", false);
        }catch (NoSuchFieldException e){
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return htmlFilter;
    });

    /**
     * XSS过滤
     *
     * @param content 需要过滤的内容
     * @return 返回过滤后的内容
     */
    public static String filter(String content) {
        return HTML_FILTER.get().filter(content);
    }
    
}