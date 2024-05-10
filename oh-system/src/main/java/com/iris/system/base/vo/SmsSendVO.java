package com.iris.system.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
* 短信发送
*
* @author 王小费 whx5710@qq.com
*/
@Schema(description = "短信发送")
public class SmsSendVO implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "手机号")
	private String mobile;

	@Schema(description = "参数Key")
	private String paramKey;

	@Schema(description = "参数Value")
	private String paramValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}