package com.iris.workflow.vo;

import com.iris.framework.common.entity.IDEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
* 自定义流程表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-12-19
*/
@Schema(description = "自定义流程表")
public class FlowVO extends IDEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Schema(description = "流程code")
	private String key;

	@Schema(description = "流程名称")
	private String name;

	@Schema(description = "bpmn的xml字符串")
	private String xml;

	@Schema(description = "svg图片字符串")
	private String svgStr;

	@Schema(description = "说明")
	private String note;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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