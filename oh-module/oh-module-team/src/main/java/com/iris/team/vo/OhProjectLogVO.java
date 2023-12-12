package com.iris.team.vo;

import com.iris.framework.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
* 项目、任务操作日志
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Schema(description = "项目、任务操作日志")
public class OhProjectLogVO extends BaseEntity {

	@Schema(description = "项目ID")
	private Long projectId;

	@Schema(description = "任务ID")
	private Long taskId;

	@Schema(description = "操作类型")
	private String operation;

	@Schema(description = "备注")
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