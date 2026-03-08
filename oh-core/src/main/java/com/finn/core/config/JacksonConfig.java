package com.finn.core.config;

import com.finn.core.utils.DateUtils;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.module.SimpleModule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JACKSON 配置
 */
@Configuration
public class JacksonConfig {

    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_PATTERN);
    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN);

    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            // 添加 LocalDateTime 的序列化与反序列化
            builder.addModule(
                    new SimpleModule()
                            // Long 的序列化
                            .addSerializer(Long.class, new LongSerializer())
                            // LocalDateTime 的序列化
                            .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer())
                            // LocalDateTime 的反序列化
                            .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer())
                            // LocalDate 的序列化
                            .addSerializer(LocalDate.class, new LocalDateSerializer())
                            // LocalDate 的反序列化
                            .addDeserializer(LocalDate.class, new LocalDateDeserializer())
            );
        };
    }

    /**
     * Long 的序列化
     */
    public static class LongSerializer extends ValueSerializer<Long> {
        @Override
        public void serialize(Long value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
            if (value != null) {
                gen.writeString(value.toString());
            }
        }
    }

    /**
     * LocalDateTime 的序列化
     */
    public static class LocalDateTimeSerializer extends ValueSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
            if (value != null) {
                gen.writeString(value.format(DATE_TIME_FORMATTER));
            } else {
                gen.writeNull();
            }
        }
    }
    /**
     * LocalDate 的序列化
     */
    public static class LocalDateSerializer extends ValueSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
            if (value != null) {
                gen.writeString(value.format(DATE_PATTERN));
            } else {
                gen.writeNull();
            }
        }
    }

    /**
     * LocalDateTime 的反序列化
     */
    public static class LocalDateTimeDeserializer extends ValueDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
            String dateString = p.getString();
            if (dateString == null || dateString.trim().isEmpty()) {
                return null;
            }
            return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
        }
    }
    /**
     * LocalDate 的反序列化
     */
    public static class LocalDateDeserializer extends ValueDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
            String dateString = p.getString();
            if (dateString == null || dateString.trim().isEmpty()) {
                return null;
            }
            return LocalDate.parse(dateString, DATE_PATTERN);
        }
    }
}
