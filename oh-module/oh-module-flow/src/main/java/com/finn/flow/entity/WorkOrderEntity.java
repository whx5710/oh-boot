package com.finn.flow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.utils.DateUtils;
import com.finn.framework.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 工单表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-23
 */
public class WorkOrderEntity extends BaseEntity {

	/**
	* 工单编码
	*/
	private String orderCode;

	/**
	* 工单来源
	*/
	private String orderSource;

	/**
	* 上报时间
	*/
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime reportTime;

	/**
	* 事发时间
	*/
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime incidentTime;

	/**
	* 结束时间
	*/
	private LocalDateTime endTime;

	/**
	* 标题
	*/
	private String title;

	/**
	* 内容
	*/
	private String comment;

	/**
	* 位置
	*/
	private String address;

	/**
	* 经度
	*/
	private BigDecimal geoX;

	/**
	* 纬度
	*/
	private BigDecimal geoY;

	/**
	* 类别
	*/
	private Integer category;

	/**
	 * json扩展字段
	 */
	private String extendJson;

	/**
	* 备注
	*/
	private String note;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public LocalDateTime getReportTime() {
		return reportTime;
	}

	public void setReportTime(LocalDateTime reportTime) {
		this.reportTime = reportTime;
	}

	public LocalDateTime getIncidentTime() {
		return incidentTime;
	}

	public void setIncidentTime(LocalDateTime incidentTime) {
		this.incidentTime = incidentTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getGeoX() {
		return geoX;
	}

	public void setGeoX(BigDecimal geoX) {
		this.geoX = geoX;
	}

	public BigDecimal getGeoY() {
		return geoY;
	}

	public void setGeoY(BigDecimal geoY) {
		this.geoY = geoY;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getExtendJson() {
		return extendJson;
	}

	public void setExtendJson(String extendJson) {
		this.extendJson = extendJson;
	}
}