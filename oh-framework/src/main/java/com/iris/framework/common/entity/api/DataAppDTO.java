package com.iris.framework.common.entity.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
* 客户端
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
public class DataAppDTO {
	/**
	 * id
	 */
	private Long id;
	@Schema(description = "客户端名称")
	private String name;

	@Schema(description = "客户端ID")
	private String clientId;

	@Schema(description = "密钥")
	private String secretKey;

	@Schema(description = "备注")
	private String remark;

	@Schema(description = "是否异步0否1是")
	private Boolean isAsync;
	// 功能号
	private String funcCode;

	private String funcName;

	/**
	 * 创建者
	 */
	private Long  creator;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	private Long  updater;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	/**
	 * 删除标记
	 */
	private Integer dbStatus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getAsync() {
		return isAsync;
	}

	public void setAsync(Boolean async) {
		isAsync = async;
	}
}