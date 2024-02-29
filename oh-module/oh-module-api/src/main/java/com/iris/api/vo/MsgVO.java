package com.iris.api.vo;

import com.iris.framework.common.entity.MetaEntity;

/**
 * 消息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-29
 */
public class MsgVO {
    private MetaEntity metaEntity;

    private Boolean isAsync;

    public MetaEntity getMetaEntity() {
        return metaEntity;
    }

    public void setMetaEntity(MetaEntity metaEntity) {
        this.metaEntity = metaEntity;
    }

    public Boolean getAsync() {
        return isAsync;
    }

    public void setAsync(Boolean async) {
        isAsync = async;
    }
}
