package com.iris.framework.common.entity;

import java.util.Map;

/**
 * 元素 Entity基类
 * 数据传输
 * @author 王小费 whx5710@qq.com
 */
public class MetaEntity extends IDEntity {

    // 功能号
    private String funcCode;
    // 客户端ID
    private String clientId;

    // 主题
    private String topic;

    // 数据
    private Map<String, Object> data;

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
