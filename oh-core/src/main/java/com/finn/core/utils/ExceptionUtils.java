package com.finn.core.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception工具类
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class ExceptionUtils {

    /**
     * 获取异常信息
     * @param e  异常
     * @return    返回异常信息
     */
    public static String getExceptionMessage(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        // 关闭IO流
        try {
            pw.close();
            sw.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return sw.toString();
    }
}