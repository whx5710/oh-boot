package com.iris.sys.quartz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 定时任务日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class ScheduleJobLogEntity {
	/**
	* id
	*/
	private Long id;

	/**
	* 任务id
	*/
	private Long jobId;

	/**
	* 任务名称
	*/
	private String jobName;

	/**
	* 任务组名
	*/
	private String jobGroup;

	/**
	* spring bean名称
	*/
	private String beanName;

	/**
	 * 执行方法
	 */
	private String method;

	/**
	* 参数
	*/
	private String params;

	/**
	* 任务状态
	*/
	private Integer status;

	/**
	* 异常信息
	*/
	private String error;

	/**
	* 耗时(单位：毫秒)
	*/
	private Long times;

	/**
	* 创建时间
	*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
}