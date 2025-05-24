package com.finn.flow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.entity.IDEntity;
import com.finn.core.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 自定义流程表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-12-19
*/
public class FlowVO extends IDEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 流程code
	 */
	@NotBlank(message = "流程编码不能为空")
	private String keyCode;

	/**
	 * 流程名称
	 */
	private String name;

	/**
	 * bpmn的xml字符串
	 */
	@NotBlank(message = "绘制的流程不能为空")
	private String xml;

	/**
	 * svg图片字符串
	 */
	private String svgStr;

	/**
	 * 标签
	 */
	private String versionTag;

	/**
	 * 说明
	 */
	private String note;

	/**
	 * 标志
	 */
	private Integer dbStatus;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

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

	public String getVersionTag() {
		return versionTag;
	}

	public void setVersionTag(String versionTag) {
		this.versionTag = versionTag;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Integer getDbStatus() {
		return dbStatus;
	}

	public void setDbStatus(Integer dbStatus) {
		this.dbStatus = dbStatus;
	}
}