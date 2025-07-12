package com.finn.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * id Entity基类
 *
 * @author 王小费 whx5710@qq.com
 */
public class IDEntity extends SuperEntity {

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
