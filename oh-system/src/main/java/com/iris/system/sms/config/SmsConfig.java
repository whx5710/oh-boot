package com.iris.system.sms.config;

/**
 * 短信配置项
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class SmsConfig {
    /**
     * 平台ID
     */
    private Long id;

    /**
     * 平台类型
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
}
