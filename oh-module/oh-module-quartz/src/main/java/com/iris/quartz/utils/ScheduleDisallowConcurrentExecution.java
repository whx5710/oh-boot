package com.iris.quartz.utils;

import org.quartz.DisallowConcurrentExecution;

/**
 * 禁止并发
 *
 * @author 王小费 whx5710@qq.com
 *
 *
 */
@DisallowConcurrentExecution
public class ScheduleDisallowConcurrentExecution extends AbstractScheduleJob {

}
