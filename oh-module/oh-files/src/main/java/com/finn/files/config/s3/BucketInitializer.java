package com.finn.files.config.s3;

import com.finn.files.config.SeaweedFSProperties;
import com.finn.files.config.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

/**
 * 初始化
 */
@Configuration
@ConditionalOnProperty(prefix = "finn.storage", value = "type", havingValue = "seaweedfs")
public class BucketInitializer {

    private static final Logger log = LoggerFactory.getLogger(BucketInitializer.class);

    private final SeaweedFSProperties properties;

    public BucketInitializer(StorageProperties storageProperties){
        this.properties = storageProperties.getSeaweedFS();
    }

    @Bean
    public CommandLineRunner initBucket(S3Client s3Client) {
        return args -> {
            String bucket = properties.getBucket();
            try {
                s3Client.headBucket(HeadBucketRequest.builder().bucket(bucket).build());
                log.info("Bucket '{}' 已存在", bucket);
            } catch (NoSuchBucketException e) {
                s3Client.createBucket(CreateBucketRequest.builder().bucket(bucket).build());
                log.info("Bucket '{}' 创建成功", bucket);
            } catch (Exception e) {
                log.warn("SeaweedFS 服务连接失败，文件服务暂不可用，不影响系统其他功能。错误：{}", e.getMessage());
            }
        };
    }
}
