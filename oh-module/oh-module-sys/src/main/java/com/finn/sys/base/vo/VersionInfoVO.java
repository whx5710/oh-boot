package com.finn.sys.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.utils.DateUtils;
import com.finn.framework.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
* 版本信息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-09-16
*/
@Schema(description = "版本信息")
public class VersionInfoVO extends BaseEntity {

	@Schema(description = "版本号")
	private String versionNum;

	@Schema(description = "发布内容")
	private String content;

	/**
	 * 封面图片
	 */
	@Schema(description = "封面图片")
	private String coverPicture;

	@Schema(description = "发布时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime releaseTime;

	// 是否当前版本
	private Boolean isCurrVersion;

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

	public void setIsCurrVersion(Boolean currVersion) {
		isCurrVersion = currVersion;
	}

	public String getCoverPicture() {
		return coverPicture;
	}

	public void setCoverPicture(String coverPicture) {
		this.coverPicture = coverPicture;
	}
}