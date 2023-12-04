package com.iris.team.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.iris.framework.common.entity.BaseEntity;

import java.util.Date;

/**
 * 项目信息表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
@TableName("oh_project")
public class OhProjectEntity extends BaseEntity {
	/**
	* 项目编码
	*/
	private String projectCode;

	/**
	* 项目名称
	*/
	private String projectName;

	/**
	* 别名
	*/
	private String projectAlias;

	/**
	* 开始时间
	*/
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Date startTime;

	/**
	* 结束时间
	*/
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Date endTime;

	/**
	* 负责人ID
	*/
	private Long director;

	/**
	 * 负责人姓名
	 */
	private String directorName;

	/**
	* 状态  0：停用   1：正常
	*/
	private Integer status;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectAlias() {
		return projectAlias;
	}

	public void setProjectAlias(String projectAlias) {
		this.projectAlias = projectAlias;
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

	public Long getDirector() {
		return director;
	}

	public void setDirector(Long director) {
		this.director = director;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}