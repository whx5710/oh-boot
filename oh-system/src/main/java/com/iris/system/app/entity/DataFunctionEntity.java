package com.iris.system.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iris.framework.common.entity.BaseEntity;

/**
 * 功能列表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-30
 */

@TableName("data_function")
public class DataFunctionEntity extends BaseEntity {

	/**
	* 功能名称
	*/
	private String name;

	/**
	* 功能号
	*/
	private String funcCode;

	/**
	* 备注
	*/
	private String remark;

	/**
	 * 是否异步0否1是
	 */
	private Boolean isAsync;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Boolean getAsync() {
		return isAsync;
	}

	public void setAsync(Boolean async) {
		isAsync = async;
	}
}