package com.finn.team.vo;

import com.finn.framework.entity.BaseEntity;

/**
* 项目、任务操作日志
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
public class OhProjectLogVO extends BaseEntity {

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 任务ID
	 */
	private Long taskId;

	/**
	 * 操作类型
	 */
	private String operation;

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

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}