package com.finn.core.entity;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.DateUtils;
import com.finn.core.utils.JsonUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;

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
            case Date date -> DateUtils.format(date, DateUtils.DATE_TIME_PATTERN);
            case LocalDateTime dateTime -> DateUtils.format(dateTime, DateUtils.DATE_TIME_PATTERN);
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
        switch (obj) {
            case null -> {
                return null;
            }
            case Integer i -> {
                return i;
            }
            case String str -> {
                try {
                    return Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    throw new ServerException("数字类型不正确，获取失败");
                }
            }
            default -> throw new ServerException("数字类型不正确，获取失败");
        }
    }

    /**
     * 获取长整型
     * @param key key
     * @return long
     */
    public Long getLong(String key){
        Object obj = this.get(key);
        if(obj == null){
            return null;
        }
        switch (obj) {
            case Long l -> {
                return l;
            }
            case Integer i -> {
                return Long.valueOf(i);
            }
            case String str -> {
                try {
                    return Long.parseLong(str);
                } catch (NumberFormatException e) {
                    throw new ServerException(str + " 数字类型不正确[Long]，获取失败");
                }
            }
            case Date date -> {
                return date.getTime();
            }
            case LocalDateTime localDateTime -> {
                return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
            }
            default -> throw new ServerException("数字类型不正确[Long]，获取失败");
        }
    }

    /**
     * 获取日期
     * @param key k
     * @param pattern 日期格式
     * @return date
     */
    public Date getDate(String key, String pattern){
        Object obj = this.get(key);
        switch (obj) {
            case Date date -> {
                return date;
            }
            case String str -> {
                try {
                    if (pattern == null || pattern.isEmpty()) {
                        return DateUtils.parse(str, DateUtils.DATE_TIME_PATTERN);
                    } else {
                        return DateUtils.parse(str, pattern);
                    }
                } catch (Exception e) {
                    throw new ServerException(str + " 日期格式不正确");
                }
            }
            case null, default -> {
                return null;
            }
        }
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
        switch (obj) {
            case LocalDateTime localDateTime -> {
                return localDateTime;
            }
            case String str -> {
                if (pattern == null || pattern.isEmpty()) {
                    return DateUtils.parseLocalDateTime(str);
                } else {
                    return DateUtils.parseLocalDateTime(str, pattern);
                }
            }
            case null, default -> {
                return null;
            }
        }
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
     * 返回布尔类型
     * @param key key
     * @return bool
     */
    public Boolean getBool(String key){
        Object obj = this.get(key);
        switch (obj) {
            case Boolean b -> {
                return b;
            }
            case String str -> {
                return !str.equalsIgnoreCase("false") && !str.equals("0");
            }
            case Integer i -> {
                return i != 0;
            }
            case null, default -> {
                return null;
            }
        }
    }


    /**
     * 对象转json 2025-02-09
     * @return json字符串
     */
    public String toJson(){
        return JsonUtils.toJsonString(this);
    }
}
