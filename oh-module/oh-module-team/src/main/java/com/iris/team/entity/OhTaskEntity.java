package com.iris.team.entity;

import com.iris.framework.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;

/**
 * 任务表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
@TableName("oh_task")
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
	private Date startTime;

	/**
	* 计划结束时间
	*/
	private Date endTime;

	/**
	* 状态（1待办项2进行中3已完成）
	*/
	private String status;

	/**
	* 备注
	*/
	private String remark;

	/**
	* 删除标识  0：正常   1：已删除
	*/
	private Integer deleted;

	// 负责人ID
	@TableField(exist=false)
	private String directorUserId;

	// 负责人
	@TableField(exist=false)
	private String directorUserName;

	//协作人ID
	@TableField(exist=false)
	private String collaboratorUserId;

	//协作人
	@TableField(exist=false)
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

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
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