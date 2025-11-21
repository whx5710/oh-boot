package com.finn.sys.config;

import com.finn.sys.enums.StorageTypeEnum;
import com.finn.sys.service.LocalStorageService;
import com.finn.sys.service.StorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 存储配置文件
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
@ConditionalOnProperty(prefix = "finn.storage", value = "enabled")
public class StorageConfiguration {

    @Bean
    public StorageService storageService(StorageProperties properties) {
        if (properties.getConfig().getType() == StorageTypeEnum.LOCAL) {
            return new LocalStorageService(properties);
        }
//        else if (properties.getConfig().getType() == StorageTypeEnum.ALIYUN) {
//            return new AliyunStorageService(properties);
//        } else if (properties.getConfig().getType() == StorageTypeEnum.TENCENT) {
//            return new TencentStorageService(properties);
//        } else if (properties.getConfig().getType() == StorageTypeEnum.QINIU) {
//            return new QiniuStorageService(properties);
//        } else if (properties.getConfig().getType() == StorageTypeEnum.HUAWEI) {
//            return new HuaweiStorageService(properties);
//        } else if (properties.getConfig().getType() == StorageTypeEnum.MINIO) {
//            return new MinioStorageService(properties);
//        }

        return null;
    }

}
