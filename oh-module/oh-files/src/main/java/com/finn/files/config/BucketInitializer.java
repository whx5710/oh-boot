package com.finn.files.config;

import org.springframework.boot.CommandLineRunner;
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
public class BucketInitializer {

    private final SeaweedFSProperties properties;

    public BucketInitializer(SeaweedFSProperties properties){
        this.properties = properties;
    }

    @Bean
    public CommandLineRunner initBucket(S3Client s3Client) {
        return args -> {
            String bucket = properties.getBucket();
            try {
                s3Client.headBucket(HeadBucketRequest.builder().bucket(bucket).build());
                System.out.println("Bucket '" + bucket + "' 已存在");
            } catch (NoSuchBucketException e) {
                s3Client.createBucket(CreateBucketRequest.builder().bucket(bucket).build());
                System.out.println("Bucket '" + bucket + "' 创建成功");
            }
        };
    }
}
