package com.finn.sys.quartz.utils;

import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;

/**
 * cron 工具类
 *
 * @author 王小费 whx5710@qq.com
 * 
 *
 */
public class CronUtils {
    /**
     * 验证Cron表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 根据给定的Cron表达式，返回下一个执行时间
     */
    public static Date getNextExecution(String cronExpression) {
        try {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
