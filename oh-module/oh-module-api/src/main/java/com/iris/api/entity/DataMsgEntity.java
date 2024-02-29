package com.iris.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iris.framework.common.entity.BaseEntity;

/**
 * 传输数据
 */
@TableName("data_message")
public class DataMsgEntity extends BaseEntity {
    // 数据字符串
    private String jsonStr;
    // 功能号
    private String funCode;
    // 客户端ID
    private String clientId;

    private String topic;

    // 状态0未处理1处理
    private String state;

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
