package com.iris.workflow.entity;

import com.iris.framework.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
/**
 * 自定义流程表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-12-19
 */
@TableName("bpmn_flow")
public class FlowEntity extends BaseEntity {
	/**
	* 流程code
	*/
	private String keyCode;

	/**
	* 流程名称
	*/
	private String name;

	/**
	* bpmn的xml字符串
	*/
	private String xml;

	/**
	 * svg图片字符串格式
	 */
	private String svgStr;
	/**
	* 说明
	*/
	private String note;

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSvgStr() {
		return svgStr;
	}

	public void setSvgStr(String svgStr) {
		this.svgStr = svgStr;
	}
}