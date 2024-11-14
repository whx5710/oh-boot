package com.iris.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期处理
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /** 时间格式-毫秒(yyyy-MM-dd HH:mm:ss:SSS) */
    public final static String DATE_TIME_MIL_PATTERN = "yyyy-MM-dd HH:mm:ss:SSS";
    /** 时间格式(HH:mm:ss) */
    public final static String TIME_PATTERN = "HH:mm:ss";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(LocalDateTime date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date 日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 日期格式化
     */
    public static String format(LocalDateTime date, String pattern) {
        if(date != null){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
            return dateTimeFormatter.format(date);
        }
        return null;
    }

    /**
     * 日期解析
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回Date
     */
    public static Date parse(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            log.error("日期转换失败！{}", e.getMessage());
        }
        return null;
    }

    /**
     * 日期解析
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String date, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, df);
    }

    /**
     * 日期解析
     * @param date  日期 默认 yyyy-MM-dd HH:mm:ss
     * @return  返回LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return LocalDateTime.parse(date, df);
    }

    /**
     * date类型转 LocalDateTime 类型
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDate(Date date){
        if(date == null){
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
