package com.finn.app.entity;

import com.finn.framework.entity.BaseEntity;

/**
 * 客户端接口授权
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */

public class DataFunctionAuthorityEntity extends BaseEntity {

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
}