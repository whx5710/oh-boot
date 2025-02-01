package com.iris.core.entity;

import com.iris.core.utils.JsonUtils;

import java.io.Serializable;

/**
 * 实体类-超类
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-12-17
 */
public class SuperEntity implements Serializable {
    /**
     * 对象转json 2023-10-11
     * @return json字符串
     */
    public String toJson(){
        return JsonUtils.toJsonString(this);
    }

    /**
     * 将对象转换成Map
     * @return
     */
    public HashDto toDto(){
        return JsonUtils.convertValue(this, HashDto.class);
    }
}
