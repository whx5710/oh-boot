package com.finn.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 客户端接口授权
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
public class DataFunctionAuthorityVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 客户端ID
	 */
	private String clientId;

	/**
	 * 功能号
	 */
	private String funcCode;

	/**
	 * 备注
	 */
	private String remark;

	private Long creator;

	private String funcName;

	private String appName;

	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

	private Long updater;

	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime updateTime;

	private Integer dbStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDbStatus() {
		return dbStatus;
	}

	public void setDbStatus(Integer dbStatus) {
		this.dbStatus = dbStatus;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}