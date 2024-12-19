package com.iris.sys.base.entity;

import java.time.LocalDateTime;

/**
 * 短信日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class SmsLogEntity {
	/**
	* id
	*/
	private Long id;

	/**
	* 平台ID
	*/
	private Long platformId;

	/**
	* 平台类型
	*/
	private Integer platform;

	/**
	* 手机号
	*/
	private String mobile;

	/**
	* 状态  0：失败   1：成功
	*/
	private Integer status;

	/**
	* 参数
	*/
	private String params;

	/**
	 * 异常信息
	 */
	private String error;

	/**
	* 创建时间
	*/
	private LocalDateTime createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
}