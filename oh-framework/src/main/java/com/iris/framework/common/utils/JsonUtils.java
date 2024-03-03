package com.iris.framework.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

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

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 未知的属性
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // 空beans
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("对象转json字符串失败！" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, clazz);
        } catch (Exception e) {
            log.error(text + " json字符串转化对象失败!！" + e.getMessage());
             throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
        if (ObjectUtils.isEmpty(bytes)) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (Exception e) {
            log.error("byte [] 转化对象失败!！" + e.getMessage());
             throw new RuntimeException(e);
        }
    }

    public static <T> T convertValue(Object fromValue, Class<T> clazz) {
        try {
            return objectMapper.convertValue(fromValue, clazz);
        } catch (Exception e) {
            log.error("对象转换失败!！" + e.getMessage());
             throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(text, typeReference);
        } catch (Exception e) {
            log.error(text + " json字符串转化对象失败！" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            log.error(text + " 转list失败！" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
