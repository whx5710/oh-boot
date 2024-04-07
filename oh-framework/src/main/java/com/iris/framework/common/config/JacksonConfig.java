package com.iris.framework.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.iris.framework.common.utils.DateUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * JACKSON 配置
 */
@Configuration
public class JacksonConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class,
                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_PATTERN)));
            builder.serializerByType(LocalDate.class,
                    new LocalDateSerializer(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN)));
            builder.serializerByType(LocalTime.class,
                    new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN)));
            builder.deserializerByType(LocalDateTime.class,
                    new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_PATTERN)));
            builder.deserializerByType(LocalDate.class,
                    new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN)));
            builder.deserializerByType(LocalTime.class,
                    new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN)));
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.failOnUnknownProperties(false);
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 禁止将 java.util.Date, Calendar 序列化为数字(时间戳)

            // 全局转化Long类型为String，解决序列化后传入前端Long类型精度丢失问题
            builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
            builder.serializerByType(Long.class,ToStringSerializer.instance);
        };
    }
}
