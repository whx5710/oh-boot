package com.finn.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.finn.core.config.JacksonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import tools.jackson.core.json.JsonWriteFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * JSON 工具类
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class JsonUtils {
    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .enable(JsonWriteFeature.ESCAPE_NON_ASCII)
            .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
            .addModule(new SimpleModule()
                    // Long 的序列化
                    .addSerializer(Long.class, new JacksonConfig.LongSerializer())
                    // LocalDateTime 的序列化
                    .addSerializer(LocalDateTime.class, new JacksonConfig.LocalDateTimeSerializer())
                    // LocalDateTime 的反序列化
                    .addDeserializer(LocalDateTime.class, new JacksonConfig.LocalDateTimeDeserializer())
                    // LocalDate 的序列化
                    .addSerializer(LocalDate.class, new JacksonConfig.LocalDateSerializer())
                    // LocalDate 的反序列化
                    .addDeserializer(LocalDate.class, new JacksonConfig.LocalDateDeserializer()))
            .build();

    private final static Logger log = LoggerFactory.getLogger(JsonUtils.class);
    /**
     * 对象转json字符串
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("对象转json字符串失败！{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转对象
     * @param text
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, clazz);
        } catch (Exception e) {
            log.error("{} json字符串转化对象失败! {}", text, e.getMessage());
             throw new RuntimeException(e);
        }
    }

    /**
     * byte数组转对象
     * @param bytes
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
        if (ObjectUtils.isEmpty(bytes)) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (Exception e) {
            log.error("byte [] 转化对象失败! {}", e.getMessage());
             throw new RuntimeException(e);
        }
    }

    /**
     * 对象转对象
     * @param fromValue
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T convertValue(Object fromValue, Class<T> clazz) {
        try {
            return objectMapper.convertValue(fromValue, clazz);
        } catch (Exception e) {
            log.error("对象转换失败!{}", e.getMessage());
             throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转 list
     * @param text
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            log.error("{} 转list失败！{}", text, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象集合转 list
     * @param fromValue
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> List<T> parseArray(Object fromValue, Class<T> clazz) {
        try {
            return objectMapper.convertValue(fromValue, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            log.error("转list失败！{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
