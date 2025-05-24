package com.finn.flow.vo;

import com.finn.core.entity.IDEntity;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
* 环节定义表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-01-31
*/
public class FlowNodeVO extends IDEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 流程定义ID
	 */
	@NotBlank(message = "流程定义ID不能为空")
	private String procDefId;

	/**
	 * 环节ID
	 */
	@NotBlank(message = "环节ID不能为空")
	private String actDefId;

	/**
	 * 环节名称
	 */
	private String nodeName;

	/**
	 * 备注
	 */
	private String note;

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}