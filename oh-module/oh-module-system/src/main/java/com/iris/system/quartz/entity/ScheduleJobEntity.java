package com.iris.system.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iris.framework.common.entity.BaseEntity;


/**
 * 定时任务
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("schedule_job")
public class ScheduleJobEntity extends BaseEntity{

	/**
	 * id
	 */
	@TableId
	private Long id;

	/**
	* 任务名称
	*/
	private String jobName;

	/**
	* 任务组名
	*/
	private String jobGroup;

	/**
	* bean名称
	*/
	private String beanName;

	/**
	 * 执行方法
	 */
	private String method;

	/**
	* 方法参数
	*/
	private String params;

	/**
	* cron表达式
	*/
	private String cronExpression;

	/**
	* 状态 
	*/
	private Integer status;

	/**
	* 是否并发  0：禁止  1：允许
	*/
	private Integer concurrent;

	/**
	* 备注
	*/
	private String remark;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
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

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(Integer concurrent) {
		this.concurrent = concurrent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}