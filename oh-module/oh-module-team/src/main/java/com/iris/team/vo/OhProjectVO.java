package com.iris.team.vo;

import com.iris.framework.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.iris.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Schema(description = "项目信息表")
public class OhProjectVO extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "项目编码")
	private String projectCode;

	@Schema(description = "项目名称")
	private String projectName;

	@Schema(description = "别名")
	private String projectAlias;

	@Schema(description = "开始时间")
	@JsonFormat(pattern = DateUtils.DATE_PATTERN)
	private Date startTime;

	@Schema(description = "结束时间")
	@JsonFormat(pattern = DateUtils.DATE_PATTERN)
	private Date endTime;

	@Schema(description = "负责人ID")
	private Long director;

	@Schema(description = "负责人姓名")
	private String directorName;

	@Schema(description = "状态  0：停用   1：正常")
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

	@Override
	public Integer getDbStatus() {
		return dbStatus;
	}

	@Override
	public void setDbStatus(Integer dbStatus) {
		this.dbStatus = dbStatus;
	}
}