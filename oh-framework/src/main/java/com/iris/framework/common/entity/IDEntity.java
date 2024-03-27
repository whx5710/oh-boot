package com.iris.framework.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fhs.core.trans.vo.TransPojo;
import com.iris.framework.common.utils.JsonUtils;

import java.io.Serializable;

/**
 * id Entity基类
 *
 * @author 王小费 whx5710@qq.com
 */
public class IDEntity implements TransPojo, Serializable {

    /**
     * id
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 对象转json 2023-10-11
     * @return json字符串
     */
    public String toJson(){
        return JsonUtils.toJsonString(this);
    }
}
