package com.iris.workflow.vo;

import com.iris.framework.common.entity.IDEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.iris.framework.common.utils.DateUtils;
import java.util.Date;

/**
* 环节运行记录表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-06
*/
@Schema(description = "环节运行记录表")
public class TaskRecordVO extends IDEntity {

	@Schema(description = "流程定义ID")
	private String procDefId;

	@Schema(description = "环节实例ID")
	private String procInstId;

	@Schema(description = "环节实例ID")
	private String actInstId;

	@Schema(description = "任务ID")
	private String taskId;

	@Schema(description = "环节key")
	private String taskDefId;

	@Schema(description = "环节名称")
	private String taskName;

	@Schema(description = "来自于环节实例ID")
	private String fromActInstId;

	@Schema(description = "来自于任务key")
	private String fromTaskId;

	@Schema(description = "来自于环节ID")
	private String fromTaskDefId;

	@Schema(description = "来自于环节名称")
	private String fromTaskName;

	@Schema(description = "当前标识，默认0，1标识当前环节")
	private Integer runMark;

	@Schema(description = "受理人ID")
	private String assignee;

	@Schema(description = "受理人")
	private String assigneeName;

	@Schema(description = "开始时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date startTime;

	@Schema(description = "结束时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date endTime;

	@Schema(description = "时长")
	private Long duration;

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getActInstId() {
		return actInstId;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskDefId() {
		return taskDefId;
	}

	public void setTaskDefId(String taskDefId) {
		this.taskDefId = taskDefId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getFromActInstId() {
		return fromActInstId;
	}

	public void setFromActInstId(String fromActInstId) {
		this.fromActInstId = fromActInstId;
	}

	public String getFromTaskId() {
		return fromTaskId;
	}

	public void setFromTaskId(String fromTaskId) {
		this.fromTaskId = fromTaskId;
	}

	public String getFromTaskDefId() {
		return fromTaskDefId;
	}

	public void setFromTaskDefId(String fromTaskDefId) {
		this.fromTaskDefId = fromTaskDefId;
	}

	public String getFromTaskName() {
		return fromTaskName;
	}

	public void setFromTaskName(String fromTaskName) {
		this.fromTaskName = fromTaskName;
	}

	public Integer getRunMark() {
		return runMark;
	}

	public void setRunMark(Integer runMark) {
		this.runMark = runMark;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
}