package com.finn.files.config;

import java.util.List;

/**
 * 配置属性类
 */
public class SeaweedFSProperties {

    /**
     * 网关地址
     */
    private String endpoint;
    private String accessKey;
    private String secretKey;
    /**
     * 默认存储桶名
     */
    private String bucket;
    private String region;
    private boolean pathStyleAccess = true;

    /**
     * 白名单文件，为空运行全部
     */
    private List<String> fileSuffix;

    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }

    public String getAccessKey() { return accessKey; }
    public void setAccessKey(String accessKey) { this.accessKey = accessKey; }

    public String getSecretKey() { return secretKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }

    public String getBucket() { return bucket; }
    public void setBucket(String bucket) { this.bucket = bucket; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public boolean isPathStyleAccess() { return pathStyleAccess; }
    public void setPathStyleAccess(boolean pathStyleAccess) { this.pathStyleAccess = pathStyleAccess; }

    public List<String> getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(List<String> fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

}