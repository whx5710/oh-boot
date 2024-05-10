package com.iris.system.storage.properties;

import com.iris.system.storage.enums.StorageTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 存储配置项
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    /**
     * 是否开启存储
     */
    private boolean enabled;
    /**
     * 通用配置项
     */
    private StorageConfig config;
    /**
     * 本地配置项
     */
    private LocalStorageProperties local;
    /**
     * 阿里云配置项
     */
    private AliyunStorageProperties aliyun;
    /**
     * 七牛云配置项
     */
    private QiniuStorageProperties qiniu;
    /**
     * 华为云配置项
     */
    private HuaweiStorageProperties huawei;
    /**
     * Minio配置项
     */
    private MinioStorageProperties minio;
    /**
     * 腾讯云配置项
     */
    private TencentStorageProperties tencent;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public StorageConfig getConfig() {
        return config;
    }

    public void setConfig(StorageConfig config) {
        this.config = config;
    }

    public LocalStorageProperties getLocal() {
        return local;
    }

    public void setLocal(LocalStorageProperties local) {
        this.local = local;
    }

    public AliyunStorageProperties getAliyun() {
        return aliyun;
    }

    public void setAliyun(AliyunStorageProperties aliyun) {
        this.aliyun = aliyun;
    }

    public QiniuStorageProperties getQiniu() {
        return qiniu;
    }

    public void setQiniu(QiniuStorageProperties qiniu) {
        this.qiniu = qiniu;
    }

    public HuaweiStorageProperties getHuawei() {
        return huawei;
    }

    public void setHuawei(HuaweiStorageProperties huawei) {
        this.huawei = huawei;
    }

    public MinioStorageProperties getMinio() {
        return minio;
    }

    public void setMinio(MinioStorageProperties minio) {
        this.minio = minio;
    }

    public TencentStorageProperties getTencent() {
        return tencent;
    }

    public void setTencent(TencentStorageProperties tencent) {
        this.tencent = tencent;
    }

    public static class StorageConfig {
        /**
         * 访问域名
         */
        private String domain;
        /**
         * 配置路径前缀
         */
        private String prefix;
        /**
         * 存储类型
         */
        private StorageTypeEnum type;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public StorageTypeEnum getType() {
            return type;
        }

        public void setType(StorageTypeEnum type) {
            this.type = type;
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.local")
    public LocalStorageProperties localStorageProperties() {
        return new LocalStorageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.aliyun")
    public AliyunStorageProperties aliyunStorageProperties() {
        return new AliyunStorageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.qiniu")
    public QiniuStorageProperties qiniuStorageProperties() {
        return new QiniuStorageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.huawei")
    public HuaweiStorageProperties huaweiStorageProperties() {
        return new HuaweiStorageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.minio")
    public MinioStorageProperties minioStorageProperties() {
        return new MinioStorageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "storage.tencent")
    public TencentStorageProperties tencentStorageProperties() {
        return new TencentStorageProperties();
    }

}
