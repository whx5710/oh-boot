package com.iris.team.entity;

import com.iris.framework.entity.BaseEntity;

import java.time.LocalDateTime;


/**
 * 任务表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
public class OhTaskEntity extends BaseEntity {
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
	private LocalDateTime startTime;

	/**
	* 计划结束时间
	*/
	private LocalDateTime endTime;

	/**
	* 状态（1待办项2进行中3已完成）
	*/
	private String status;

	/**
	* 备注
	*/
	private String remark;

	/**
	* 数据状态标识  1：正常   0：已删除
	*/
	private Integer dbStatus;

	// 负责人ID
	private String directorUserId;

	// 负责人
	private String directorUserName;

	//协作人ID
	private String collaboratorUserId;

	//协作人
	private String collaboratorUserName;

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

	@Override
	public Integer getDbStatus() {
		return dbStatus;
	}

	@Override
	public void setDbStatus(Integer dbStatus) {
		this.dbStatus = dbStatus;
	}

	public String getDirectorUserId() {
		return directorUserId;
	}

	public void setDirectorUserId(String directorUserId) {
		this.directorUserId = directorUserId;
	}

	public String getDirectorUserName() {
		return directorUserName;
	}

	public void setDirectorUserName(String directorUserName) {
		this.directorUserName = directorUserName;
	}

	public String getCollaboratorUserId() {
		return collaboratorUserId;
	}

	public void setCollaboratorUserId(String collaboratorUserId) {
		this.collaboratorUserId = collaboratorUserId;
	}

	public String getCollaboratorUserName() {
		return collaboratorUserName;
	}

	public void setCollaboratorUserName(String collaboratorUserName) {
		this.collaboratorUserName = collaboratorUserName;
	}
}