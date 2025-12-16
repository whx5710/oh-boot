package com.finn.app.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

/**
 * 客户端
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
@TableName("data_app")
public class DataAppEntity extends BaseEntity {

	/**
	* 客户端名称
	*/
	private String name;

	/**
	* 客户端ID
	*/
	@TableField("client_id")
	private String clientId;

	/**
	* 密钥
	*/
	@TableField("secret_key")
	private String secretKey;

	/**
	* 备注
	*/
	private String remark;

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
}