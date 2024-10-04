package com.iris.sys.base.entity;

import com.iris.framework.common.entity.BaseEntity;

/**
 * 短信平台
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class SmsPlatformEntity extends BaseEntity {
    /**
     * 平台类型  0：阿里云   1：腾讯云   2：七牛云    3：华为云
     */
    private Integer platform;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信模板
     */
    private String templateId;

    /**
     * 短信应用的ID，如：腾讯云等
     */
    private String appId;

    /**
     * 腾讯云国际短信、华为云等需要
     */
    private String senderId;

    /**
     * 接入地址，如：华为云
     */
    private String url;

    /**
     * AccessKey
     */
    private String accessKey;

    /**
     * SecretKey
     */
    private String secretKey;

    /**
     * 状态  0：禁用   1：启用
     */
    private Integer status;

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
}