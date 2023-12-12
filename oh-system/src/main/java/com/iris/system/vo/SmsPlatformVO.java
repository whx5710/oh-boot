package com.iris.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iris.framework.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短信平台
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Schema(description = "短信平台")
public class SmsPlatformVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "平台类型  0：阿里云   1：腾讯云")
    private Integer platform;

    @Schema(description = "短信签名")
    private String signName;

    @Schema(description = "短信模板")
    private String templateId;

    @Schema(description = "短信应用的ID，如：腾讯云等")
    private String appId;

    @Schema(description = "腾讯云国际短信、华为云等需要")
    private String senderId;

    @Schema(description = "接入地址，如：华为云")
    private String url;

    @Schema(description = "AccessKey")
    private String accessKey;

    @Schema(description = "SecretKey")
    private String secretKey;

    @Schema(description = "状态  0：禁用   1：启用")
    private Integer status;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}