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

    // 状态0未处理1处理2未找到对应的服务类3业务处理失败
    private String state;

    // 备注
    private String note;

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

    /**
     * 状态0未处理1处理2未找到对应的服务类3业务处理失败
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
