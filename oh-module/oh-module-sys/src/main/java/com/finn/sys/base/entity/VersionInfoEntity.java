package com.finn.sys.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.finn.core.entity.IDEntity;
import com.finn.core.utils.DateUtils;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 版本信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-09-16
 */
@TableName("sys_version_info")
public class VersionInfoEntity extends IDEntity {

	/**
	* 版本号
	*/
	@TableField("version_num")
	private String versionNum;

	/**
	* 发布内容
	*/
	private String content;

	/**
	 * 封面图片
	 */
	@TableField("cover_picture")
	private String coverPicture;

	/**
	* 发布时间
	*/
	@TableField("release_time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime releaseTime;

	// 是否当前版本
	@TableField("is_curr_version")
	private Boolean isCurrVersion;

	/**
	 * 创建者
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long  creator;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@TableField("create_time")
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long  updater;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@TableField("update_time")
	private LocalDateTime updateTime;

	/**
	 * 数据状态标记，0删除1有效
	 */
	@TableField("db_status")
	private Integer dbStatus = 1;

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(LocalDateTime releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Boolean getCurrVersion() {
		return isCurrVersion;
	}

	public void setCurrVersion(Boolean currVersion) {
		isCurrVersion = currVersion;
	}

	public Boolean getIsCurrVersion() {
		return isCurrVersion;
	}

	public void setIsCurrVersion(Boolean currVersion) {
		isCurrVersion = currVersion;
	}

	public String getCoverPicture() {
		return coverPicture;
	}

	public void setCoverPicture(String coverPicture) {
		this.coverPicture = coverPicture;
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

	public Integer getDbStatus() {
		return dbStatus;
	}

	public void setDbStatus(Integer dbStatus) {
		this.dbStatus = dbStatus;
	}
}