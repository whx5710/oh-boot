package com.finn.framework.entity;

import com.finn.framework.exception.ServerException;
import com.finn.framework.utils.DateUtils;
import com.finn.framework.utils.JsonUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * hash dto 集合
 * @author 王小费 whx5710@qq.com
 */
public class HashDto extends HashMap<String, Object> {

    /**
     * 字符串
     * @param key key
     * @return 字符串
     */
    public String getStr(String key){
        Object obj = this.get(key);
        return switch (obj) {
            case null -> null;
            case Date date -> DateUtils.format(date);
            case LocalDateTime dateTime -> DateUtils.format(dateTime);
            default -> obj.toString();
        };
    }

    /**
     * 获取整型
     * @param key key
     * @return int
     */
    public Integer getInt(String key){
        Object obj = this.get(key);
        return switch (obj) {
            case null -> null;
            case Integer i -> i;
            case String str -> {
                try {
                    yield Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    throw new ServerException("数字类型不正确，获取失败");
                }
            }
            default -> throw new ServerException("数字类型不正确，获取失败");
        };
    }

    /**
     * 获取长整型
     * @param key key
     * @return long
     */
    public Long getLong(String key){
        Object obj = this.get(key);
        return switch (obj) {
            case null -> null;
            case Long l -> l;
            case Integer i -> Long.valueOf(i);
            case String str -> {
                try {
                    yield Long.parseLong(str);
                } catch (NumberFormatException e) {
                    throw new ServerException(str + " 数字类型不正确[Long]，获取失败");
                }
            }
            case Date date -> date.getTime();
            case LocalDateTime localDateTime -> localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
            default -> throw new ServerException("数字类型不正确[Long]，获取失败");
        };
    }

    /**
     * 获取日期
     * @param key k
     * @param pattern 日期格式
     * @return date
     */
    public Date getDate(String key, String pattern){
        Object obj = this.get(key);
        return switch (obj) {
            case Date date -> date;
            case String str -> {
                try {
                    yield DateUtils.parse(str, (pattern == null || pattern.isEmpty()) ? DateUtils.DATE_TIME_PATTERN : pattern);
                } catch (Exception e) {
                    throw new ServerException(str + " 日期格式不正确");
                }
            }
            case null, default -> null;
        };
    }

    /**
     * 获取日期和
     * @param key key
     * @return date
     */
    public Date getDate(String key){
        return getDate(key, null);
    }

    /**
     * 获取日期
     * @param key k
     * @param pattern 日期格式
     * @return date
     */
    public LocalDateTime getLocalDateTime(String key, String pattern){
        Object obj = this.get(key);
        return switch (obj) {
            case LocalDateTime localDateTime -> localDateTime;
            case String str -> (pattern == null || pattern.isEmpty()) ? DateUtils.parseLocalDateTime(str) : DateUtils.parseLocalDateTime(str, pattern);
            case null, default -> null;
        };
    }

    /**
     * 获取日期和
     * @param key key
     * @return date
     */
    public LocalDateTime getLocalDateTime(String key){
        return getLocalDateTime(key, null);
    }

    /**
     * 根据key获取对应的HashDto
     * @param key key
     * @return HashDto
     */
    public HashDto getDto(String key){
        Object obj = this.get(key);
        return switch (obj) {
            case null -> null;
            case HashDto dto -> dto;
            case Map<?, ?> map -> {
                HashDto dto = new HashDto();
                dto.putAll((Map<String, Object>) map);
                yield dto;
            }
            default -> throw new ServerException("HashDto 类型不正确，获取失败");
        };
    }
    
    /**
     * 返回布尔类型
     * @param key key
     * @return bool
     */
    public Boolean getBool(String key){
        Object obj = this.get(key);
        return switch (obj) {
            case Boolean b -> b;
            case String str -> !str.equalsIgnoreCase("false") && !str.equals("0");
            case Integer i -> i != 0;
            case null, default -> null;
        };
    }


    /**
     * 对象转json 2025-02-09
     * @return json字符串
     */
    public String toJson(){
        return JsonUtils.toJsonString(this);
    }
}
