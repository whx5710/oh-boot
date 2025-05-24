package com.finn.team.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.utils.DateUtils;
import com.finn.framework.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
public class OhProjectVO extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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
	@JsonFormat(pattern = DateUtils.DATE_PATTERN)
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = DateUtils.DATE_PATTERN)
	private LocalDateTime endTime;

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

	/**
	 * 数据状态标识  1：正常   0：已删除
	 */
	private Integer dbStatus;

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

	@Override
	public Integer getDbStatus() {
		return dbStatus;
	}

	@Override
	public void setDbStatus(Integer dbStatus) {
		this.dbStatus = dbStatus;
	}
}