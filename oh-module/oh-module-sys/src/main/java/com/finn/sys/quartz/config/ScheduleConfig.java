package com.finn.sys.quartz.config;

import com.finn.core.constant.Constant;
import com.finn.framework.common.properties.SysDataSourceProperties;
import com.finn.framework.datasource.config.DynamicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * 定时任务配置
 *
 * @author 王小费 whx5710@qq.com
 *
 */
//@Configuration
public class ScheduleConfig {
    @Value("spring.datasource.driver-class-name")
    private String driver;

    private final SysDataSourceProperties sysDataSourceProperties;

    public ScheduleConfig(SysDataSourceProperties sysDataSourceProperties){
        this.sysDataSourceProperties = sysDataSourceProperties;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        // quartz参数
        Properties prop = new Properties();
        prop.put("org.quartz.scheduler.instanceName", "OhScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        // 线程池配置
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "20");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        // jobStore配置
        prop.put("org.quartz.jobStore.class", "org.springframework.scheduling.quartz.LocalDataSourceJobStore");
        // 集群配置
        prop.put("org.quartz.jobStore.isClustered", "true");
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");

        prop.put("org.quartz.jobStore.misfireThreshold", "12000");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");

        // PostgreSQL数据库配置
        if (Constant.PGSQL_DRIVER.equals(driver)) {
            prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
        }
        return getSchedulerFactoryBean((DynamicDataSource) dataSource, prop);
    }

    /**
     * 数据源
     * 根据实际情况使用数据源
     * @param dataSource
     * @param prop
     * @return
     */
    private SchedulerFactoryBean getSchedulerFactoryBean(DynamicDataSource dataSource, Properties prop) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setSchedulerName("OhScheduler");
        // 切换数据源，使用系统内置数据源；根据实际情况使用数据源
        Map<String, DataSource> map = dataSource.getDynamicDataSources();
        // 使用系统管理库
        factory.setDataSource(map.get(sysDataSourceProperties.getSysDefault()));
        factory.setQuartzProperties(prop);
        // 延时启动
        factory.setStartupDelay(10);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        // 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        return factory;
    }
}
