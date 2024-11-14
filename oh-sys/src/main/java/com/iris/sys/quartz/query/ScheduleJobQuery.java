package com.iris.sys.quartz.query;

import com.iris.framework.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
* 定时任务查询
*
* @author 王小费 whx5710@qq.com
*/
@Schema(description = "定时任务查询")
public class ScheduleJobQuery extends Query {
    @Schema(description = "任务名称")
    private String jobName;

    @Schema(description = "任务组名")
    private String jobGroup;

    @Schema(description = "状态")
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