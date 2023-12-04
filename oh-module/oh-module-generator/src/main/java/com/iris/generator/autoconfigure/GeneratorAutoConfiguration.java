package com.iris.generator.autoconfigure;

import com.iris.generator.config.template.GeneratorConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * spring boot starter AutoConfiguration
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Configuration
@ComponentScan(basePackages = {"com.iris.generator"})
@EnableConfigurationProperties(GeneratorProperties.class)
public class GeneratorAutoConfiguration {
    private final GeneratorProperties properties;

    public GeneratorAutoConfiguration(GeneratorProperties properties) {
        this.properties = properties;
    }

    @Bean
    GeneratorConfig generatorConfig() {
        return new GeneratorConfig(properties.getTemplate());
    }

}
