package com.finn.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.utils.DateUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 功能列表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-30
*/
public class DataFunctionVO implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 是否异步0否1是
	 */
	private Boolean isAsync;
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
	 * 是否选中
	 */
	private Boolean checkedFunc;

	private Long creator;

	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

	private Long updater;

	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getAsync() {
		return isAsync;
	}

	public void setAsync(Boolean async) {
		isAsync = async;
	}

	public Boolean getCheckedFunc() {
		return checkedFunc;
	}

	public void setCheckedFunc(Boolean checkedFunc) {
		this.checkedFunc = checkedFunc;
	}
}