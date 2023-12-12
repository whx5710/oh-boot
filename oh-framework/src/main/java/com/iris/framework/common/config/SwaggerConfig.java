package com.iris.framework.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 *
 * @author 王小费
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.iris"};
        return GroupedOpenApi.builder().group("OhBoot")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("王小费 whx5710@qq.com");

        return new OpenAPI().info(new Info()
                .title("OhBoot")
                .description("OhBoot")
                .contact(contact)
                .version("3.0")
                .termsOfService("https://www")
                .license(new License().name("MIT")
                        .url("https://www")));
    }

}