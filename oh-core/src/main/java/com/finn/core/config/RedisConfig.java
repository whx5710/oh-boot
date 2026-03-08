package com.finn.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis配置
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 设置Key的序列化方式为String [citation:3]
        template.setKeySerializer(RedisSerializer.string());
        // 设置Hash Key的序列化方式也为String
        template.setHashKeySerializer(RedisSerializer.string());

        // 设置Value的序列化方式为JSON [citation:3]
        template.setValueSerializer(RedisSerializer.json());
        // 设置Hash Value的序列化方式也为JSON
        template.setHashValueSerializer(RedisSerializer.json());

        // 初始化模板配置
        template.afterPropertiesSet();
        return template;
    }
}
