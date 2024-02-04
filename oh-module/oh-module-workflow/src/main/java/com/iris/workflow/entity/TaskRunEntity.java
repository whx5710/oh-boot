package com.iris.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iris.framework.common.entity.BaseEntity;


/**
 * 环节运行表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-03
 */
@TableName("bpmn_task_run")
public class TaskRunEntity extends BaseEntity {

	/**
	* 流程定义ID
	*/
	private String procDefId;

	/**
	* 环节实例ID
	*/
	private String procInstId;

	/**
	* 环节key
	*/
	private String actDefId;

	/**
	* 环节名称
	*/
	private String nodeName;

	/**
	* 任务ID
	*/
	private String taskId;

	/**
	* 来自于环节ID
	*/
	private String fromActDefId;

	/**
	* 来自于环节名称
	*/
	private String fromNodeName;

	/**
	* 来自于任务key
	*/
	private String fromTaskId;

	/**
	 * 当前标识，默认0，1标识当前环节
	 */
	private int runMark;

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFromActDefId() {
		return fromActDefId;
	}

	public void setFromActDefId(String fromActDefId) {
		this.fromActDefId = fromActDefId;
	}

	public String getFromNodeName() {
		return fromNodeName;
	}

	public void setFromNodeName(String fromNodeName) {
		this.fromNodeName = fromNodeName;
	}

	public String getFromTaskId() {
		return fromTaskId;
	}

	public void setFromTaskId(String fromTaskId) {
		this.fromTaskId = fromTaskId;
	}

	public int getRunMark() {
		return runMark;
	}

	public void setRunMark(int runMark) {
		this.runMark = runMark;
	}
}