package com.iris.sys.quartz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iris.core.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 定时任务日志
*
* @author 王小费 whx5710@qq.com
*/
@Schema(description = "定时任务日志")
public class ScheduleJobLogVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "任务id")
	private Long jobId;

	@Schema(description = "任务名称")
	private String jobName;

	@Schema(description = "任务组名")
	private String jobGroup;

	@Schema(description = "spring bean名称")
	private String beanName;

	@Schema(description = "执行方法")
	private String method;

	@Schema(description = "参数")
	private String params;

	@Schema(description = "任务状态")
	private Integer status;

	@Schema(description = "异常信息")
	private String error;

	@Schema(description = "耗时(单位：毫秒)")
	private Integer times;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

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

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
}