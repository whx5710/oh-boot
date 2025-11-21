package com.finn.sys.config;

import com.finn.sys.enums.StorageTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

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
     * 通用配置项
     */
    private StorageConfig config;
    /**
     * 本地配置项
     */
    private LocalStorageProperties local;
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

    public MinioStorageProperties getMinio() {
        return minio;
    }

    public void setMinio(MinioStorageProperties minio) {
        this.minio = minio;
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
    @ConfigurationProperties(prefix = "finn.storage.local")
    public LocalStorageProperties localStorageProperties() {
        return new LocalStorageProperties();
    }

    /*@Bean
    @ConfigurationProperties(prefix = "finn.storage.minio")
    public MinioStorageProperties minioStorageProperties() {
        return new MinioStorageProperties();
    }*/

}
