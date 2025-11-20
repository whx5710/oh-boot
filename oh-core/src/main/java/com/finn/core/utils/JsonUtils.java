package com.finn.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * JSON 工具类
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final static Logger log = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 日期格式化
     * @return
     */
    public static JavaTimeModule getJavaTimeModule(){
        // 日期时间处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // LocalDateTime
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_PATTERN)));
        // LocalDate
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN)));
        // LocalTime
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN)));
        // date
        javaTimeModule.addSerializer(Date.class,new DateSerializer(false,new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN)));
        javaTimeModule.addDeserializer(Date.class,new DateDeserializers.DateDeserializer());
        return javaTimeModule;
    }

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 未知的属性
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // 禁止将 java.util.Date, Calendar 序列化为数字(时间戳)
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 空beans
        objectMapper.registerModule(getJavaTimeModule());
    }
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

    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(text, typeReference);
        } catch (Exception e) {
            log.error("{} json字符串转化对象失败！{}", text, e.getMessage());
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
