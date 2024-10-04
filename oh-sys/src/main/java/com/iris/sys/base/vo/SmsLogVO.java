package com.iris.sys.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iris.framework.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 短信日志
*
* @author 王小费 whx5710@qq.com
*/
@Schema(description = "短信日志")
public class SmsLogVO implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "平台ID")
	private Long platformId;

	@Schema(description = "平台类型")
	private Integer platform;

	@Schema(description = "手机号")
	private String mobile;

	@Schema(description = "状态  0：失败   1：成功")
	private Integer status;

	@Schema(description = "参数")
	private String params;

	@Schema(description = "异常信息")
	private String error;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
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