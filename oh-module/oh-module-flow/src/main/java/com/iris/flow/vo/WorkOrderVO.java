package com.iris.flow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iris.core.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 工单表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-23
*/
@Schema(description = "工单表")
public class WorkOrderVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@Schema(description = "工单编码")
	private String orderCode;

	@Schema(description = "工单来源")
	private String orderSource;

	@Schema(description = "上报时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date reportTime;

	@Schema(description = "事发时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date incidentTime;

	@Schema(description = "结束时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date endTime;

	@Schema(description = "标题")
	private String title;

	@Schema(description = "内容")
	private String comment;

	@Schema(description = "位置")
	private String address;

	@Schema(description = "经度")
	private BigDecimal geoX;

	@Schema(description = "纬度")
	private BigDecimal geoY;

	@Schema(description = "类别")
	private Integer category;

	@Schema(description = "备注")
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Date getIncidentTime() {
		return incidentTime;
	}

	public void setIncidentTime(Date incidentTime) {
		this.incidentTime = incidentTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
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
}