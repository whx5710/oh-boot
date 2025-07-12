package com.finn.sys.quartz.query;

import com.finn.framework.query.Query;

/**
* 定时任务查询
*
* @author 王小费 whx5710@qq.com
*/
public class ScheduleJobQuery extends Query {
    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 状态
     */
    private Integer status;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}