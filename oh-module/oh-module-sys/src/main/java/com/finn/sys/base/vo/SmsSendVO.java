package com.finn.sys.base.vo;

import java.io.Serial;
import java.io.Serializable;

/**
* 短信发送
*
* @author 王小费 whx5710@qq.com
*/
public class SmsSendVO implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;
	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 参数Key
	 */
	private String paramKey;

	/**
	 * 参数Value
	 */
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