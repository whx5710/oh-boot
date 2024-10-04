package com.iris.sys.storage.config;

import com.iris.sys.storage.enums.StorageTypeEnum;
import com.iris.sys.storage.properties.LocalStorageProperties;
import com.iris.sys.storage.properties.StorageProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 本地资源映射配置
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Configuration
@ConditionalOnProperty(prefix = "iris.storage", value = "enabled")
public class LocalResourceConfiguration implements WebMvcConfigurer {
    @Resource
    private StorageProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 如果不是本地存储，则返回
        if (properties.getConfig().getType() != StorageTypeEnum.LOCAL) {
            return;
        }

        LocalStorageProperties local = properties.getLocal();
        registry.addResourceHandler("/" + local.getUrl() + "/**")
                .addResourceLocations("file:" + local.getPath() + "/");
    }
}