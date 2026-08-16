package com.finn.files.config;

import com.finn.files.service.StorageService;
import com.finn.files.service.impl.LocalStorageService;
import com.finn.files.service.impl.SeaweedFSService;
import com.finn.framework.cache.RedisCache;
import com.finn.framework.exception.ServerException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/**
 * 存储配置文件
 *
 * @author 王小费 whx5710@qq.com
 */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
@ConditionalOnProperty(prefix = "finn.storage", value = "enabled", havingValue = "true")
public class StorageConfiguration {

    /**
     * 本地存储服务
     */
    @Bean
    @ConditionalOnProperty(prefix = "finn.storage", value = "type", havingValue = "local")
    public StorageService localStorageService(StorageProperties properties, ObjectProvider<RedisCache> redisCacheProvider) {
        LocalStorageService service = new LocalStorageService(properties);
        service.setRedisCache(redisCacheProvider.getIfAvailable());
        return service;
    }

    /**
     * SeaweedFS 存储服务
     */
    @Bean
    @ConditionalOnProperty(prefix = "finn.storage", value = "type", havingValue = "seaweedfs")
    public StorageService seaweedFSService(S3Client s3Client, S3Presigner s3Presigner,
                                           StorageProperties properties,
                                           ObjectProvider<RedisCache> redisCacheProvider) {
        return new SeaweedFSService(s3Client, s3Presigner, properties.getSeaweedFS(), properties, redisCacheProvider.getIfAvailable());
    }

    /**
     * 不支持的存储类型兜底，启动时即报错
     */
    @Bean
    @ConditionalOnProperty(prefix = "finn.storage", value = "type", havingValue = "minio")
    public StorageService minioStorageService(StorageProperties properties) {
        throw new ServerException("暂不支持 minio 存储类型: " + properties.getType());
    }
}
