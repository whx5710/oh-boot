package com.iris.system.app.entity;

import com.iris.framework.common.entity.BaseEntity;

/**
 * 传输数据
 */
public class DataMsgEntity extends BaseEntity {

    // 数据字符串
    private String jsonStr;
    // 功能号
    private String funcCode;
    // 客户端ID
    private String clientId;

    private String topic;

    // 状态0未处理1处理2未找到对应的服务类3业务处理失败
    private String state;
    // 响应消息
    private String resultMsg;

    // 备注
    private String note;

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

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

    /**
     * 状态0未处理1处理2未找到对应的服务类3业务处理失败
     * @return
     */
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

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
