package com.finn.files.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.time.Duration;

/**
 * S3 客户端配置
 */
@Configuration
@ConditionalOnProperty(prefix = "seaweedfs.s3", value = "enabled", havingValue = "true", matchIfMissing = true)
public class S3Config {

    private final SeaweedFSProperties properties;

    public S3Config(SeaweedFSProperties properties){
        this.properties = properties;
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(properties.getEndpoint()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())
                        )
                )
                .region(Region.of(properties.getRegion()))
                .serviceConfiguration(
                        S3Configuration.builder()
                        .pathStyleAccessEnabled(properties.isPathStyleAccess())
                        .build()
                )
                .overrideConfiguration(cfg -> cfg
                        .apiCallTimeout(Duration.ofMinutes(10)) // 超时单次2分钟/整体10分钟，避免大文件超时
                        .apiCallAttemptTimeout(Duration.ofMinutes(2))
                )
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .endpointOverride(URI.create(properties.getEndpoint()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())
                        )
                )
                .region(Region.of(properties.getRegion()))
                .serviceConfiguration(
                        S3Configuration.builder()
                        .pathStyleAccessEnabled(properties.isPathStyleAccess())
                        .build()
                )
                .build();
    }
}
