package com.finn.team.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.utils.DateUtils;
import com.finn.framework.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 任务表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
public class OhTaskVO extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属项目
	 */
	private Long projectId;

	/**
	 * 任务标题
	 */
	private String taskTitle;

	/**
	 * 任务描述
	 */
	private String note;

	/**
	 * 1任务2需求3设计4缺陷9其他
	 */
	private String taskType;

	/**
	 * 父级任务ID
	 */
	private Long parentId;

	/**
	 * 是否有子任务
	 */
	private Integer hasChild;

	/**
	 * 计划开始时间
	 */
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime startTime;

	/**
	 * 计划结束时间
	 */
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime endTime;

	/**
	 * 状态（1待办项2进行中3已完成）
	 */
	private String status;

	/**
	 * 备注
	 */
	private String remark;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getHasChild() {
		return hasChild;
	}

	public void setHasChild(Integer hasChild) {
		this.hasChild = hasChild;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}