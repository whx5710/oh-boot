package com.finn.flow.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

/**
 * 环节定义表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-01-31
 */
@TableName("bpmn_flow_node")
public class FlowNodeEntity extends BaseEntity {

	/**
	* 流程定义ID
	*/
	@TableField("proc_def_id")
	private String procDefId;

	/**
	* 环节ID
	*/
	@TableField("act_def_id")
	private String actDefId;

	/**
	* 环节名称
	*/
	@TableField("node_name")
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