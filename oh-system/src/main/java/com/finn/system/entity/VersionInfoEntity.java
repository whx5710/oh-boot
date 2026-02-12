package com.finn.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.utils.DateUtils;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 版本信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-09-16
 */
@TableName("sys_version_info")
public class VersionInfoEntity extends BaseEntity {

	/**
	* 版本号
	*/
	@TableField("version_num")
	private String versionNum;

	/**
	 * 标题
	 */
	private String title;

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

	/**
	 * 是否当前版本
	 */
	@TableField("is_curr_version")
	private Boolean isCurrVersion;

	/**
	 * 是否需要重新登录
	 */
	@TableField("re_login")
	private Boolean reLogin = false;

	/**
	 * 备注
	 */
	private String remark;

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

	public Boolean getIsCurrVersion() {
		return isCurrVersion;
	}

	public void setIsCurrVersion(Boolean isCurrVersion) {
		this.isCurrVersion = isCurrVersion;
	}

	public String getCoverPicture() {
		return coverPicture;
	}

	public void setCoverPicture(String coverPicture) {
		this.coverPicture = coverPicture;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getReLogin() {
		return reLogin;
	}

	public void setReLogin(Boolean reLogin) {
		this.reLogin = reLogin;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}