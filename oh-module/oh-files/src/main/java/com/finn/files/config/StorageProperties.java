package com.finn.files.config;

import com.finn.files.enums.StorageTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 存储配置项
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@ConfigurationProperties(prefix = "finn.storage")
public class StorageProperties {
    /**
     * 是否开启存储
     */
    private boolean enabled;
    /**
     * 存储类型 local、seaweedfs、minio
     */
    private StorageTypeEnum type;

    /**
     * 是否缓存文件信息
     */
    private boolean cacheRecord = false;
    /**
     * 本地配置项
     */
    private LocalStorageProperties local;
    /**
     * seaweedFS配置
     */
    private SeaweedFSProperties seaweedFS;
    /**
     * Minio配置项
     */
    private MinioStorageProperties minio;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public StorageTypeEnum getType() {
        return type;
    }

    public void setType(StorageTypeEnum type) {
        this.type = type;
    }

    public boolean isCacheRecord() {
        return cacheRecord;
    }

    public void setCacheRecord(boolean cacheRecord) {
        this.cacheRecord = cacheRecord;
    }

    public LocalStorageProperties getLocal() {
        return local;
    }

    public void setLocal(LocalStorageProperties local) {
        this.local = local;
    }

    public MinioStorageProperties getMinio() {
        return minio;
    }

    public void setMinio(MinioStorageProperties minio) {
        this.minio = minio;
    }

    public SeaweedFSProperties getSeaweedFS() {
        return seaweedFS;
    }

    public void setSeaweedFS(SeaweedFSProperties seaweedFS) {
        this.seaweedFS = seaweedFS;
    }

}
