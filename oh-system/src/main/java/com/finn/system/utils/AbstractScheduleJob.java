package com.finn.system.utils;

import com.finn.core.utils.ExceptionUtils;
import com.finn.framework.utils.ServiceFactory;
import com.finn.system.entity.ScheduleJobEntity;
import com.finn.system.entity.ScheduleJobLogEntity;
import com.finn.system.enums.ScheduleStatusEnum;
import com.finn.system.service.ScheduleJobLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;

public abstract class AbstractScheduleJob implements Job {
    private static final ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    private final Logger log = LoggerFactory.getLogger(AbstractScheduleJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        ScheduleJobEntity scheduleJob = new ScheduleJobEntity();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY), scheduleJob);

        try {
            threadLocal.set(new Date());
            doExecute(scheduleJob);
            saveLog(scheduleJob, null);
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：{}", scheduleJob.getId(), e);
            saveLog(scheduleJob, e);
        }
    }

    /**
     * 执行spring bean方法
     */
    protected void doExecute(ScheduleJobEntity scheduleJob) throws Exception {
        log.info("准备执行任务，任务ID：{}", scheduleJob.getId());

        // Object bean = SpringUtil.getBean(scheduleJob.getBeanName());
        Object bean = ServiceFactory.getBean(scheduleJob.getBeanName(), Object.class);
        Method method = bean.getClass().getDeclaredMethod(scheduleJob.getMethod(), String.class);
        method.invoke(bean, scheduleJob.getParams());

        log.info("任务执行完毕，任务ID：{}", scheduleJob.getId());
    }

    /**
     * 保存 log
     */
    protected void saveLog(ScheduleJobEntity scheduleJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        // 执行总时长
        long times = System.currentTimeMillis() - startTime.getTime();

        // 保存执行记录
        ScheduleJobLogEntity log = new ScheduleJobLogEntity();
        log.setJobId(scheduleJob.getId());
        log.setJobName(scheduleJob.getJobName());
        log.setJobGroup(scheduleJob.getJobGroup());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethod(scheduleJob.getMethod());
        log.setParams(scheduleJob.getParams());
        log.setTimes(times);
        log.setCreateTime(LocalDateTime.now());

        if (e != null) {
            log.setStatus(ScheduleStatusEnum.PAUSE.getValue());
            String error = ExceptionUtils.getExceptionMessage(e).substring(0,2000);
            log.setError(error);
        } else {
            log.setStatus(ScheduleStatusEnum.NORMAL.getValue());
        }

        // 保存日志
        // SpringUtil.getBean(ScheduleJobLogService.class).save(log);
        ServiceFactory.getBean("scheduleJobLogService", ScheduleJobLogService.class).save(log);
    }

}
