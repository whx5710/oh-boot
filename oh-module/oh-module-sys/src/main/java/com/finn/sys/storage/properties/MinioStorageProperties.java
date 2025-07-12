package com.finn.sys.storage.properties;


/**
 * Minio存储配置项
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class MinioStorageProperties {
    private String endPoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
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

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
