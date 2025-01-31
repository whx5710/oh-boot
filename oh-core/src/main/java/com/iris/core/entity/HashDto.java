package com.iris.core.entity;

import com.iris.core.exception.ServerException;
import com.iris.core.utils.DateUtils;

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
        if(obj instanceof Date date){
            return DateUtils.format(date, DateUtils.DATE_TIME_PATTERN);
        }else if(obj instanceof LocalDateTime dateTime){
            return DateUtils.format(dateTime, DateUtils.DATE_TIME_PATTERN);
        }else{
            return obj.toString();
        }
    }

    /**
     * 获取整型
     * @param key key
     * @return int
     */
    public int getInt(String key){
        Object obj = this.get(key);
        if(obj instanceof Integer i){
            return i;
        }else if(obj instanceof String str){
            try {
                return Integer.parseInt(str);
            }catch (NumberFormatException e){
                throw new ServerException("数字类型不正确，获取失败");
            }
        }else{
            throw new ServerException("数字类型不正确，获取失败");
        }
    }

    /**
     * 获取长整型
     * @param key key
     * @return long
     */
    public Long getLong(String key){
        Object obj = this.get(key);
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
            case null, default -> throw new ServerException("数字类型不正确[Long]，获取失败");
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
        if(obj instanceof Date date){
            return date;
        }else if(obj instanceof String str){
            try {
                if(pattern == null || pattern.isEmpty()){
                    return DateUtils.parse(str, DateUtils.DATE_TIME_PATTERN);
                }else{
                    return DateUtils.parse(str, pattern);
                }
            }catch (Exception e){
                throw new ServerException(str + " 日期格式不正确");
            }
        }else{
            return null;
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
        if(obj instanceof LocalDateTime localDateTime){
            return localDateTime;
        }else if(obj instanceof String str){
            if(pattern == null || pattern.isEmpty()){
                return DateUtils.parseLocalDateTime(str);
            }else{
                return DateUtils.parseLocalDateTime(str, pattern);
            }
        }else{
            return null;
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
}
