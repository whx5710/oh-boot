package com.finn.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.finn.core.utils.DateUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 接口参数数据
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-03-24
 */
public class DataMsgVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 数据字符串
     */
    private String jsonStr;
    /**
     * 功能号
     */
    private String funcCode;
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端
     */
    private String clientName;

    private String funcCodeName;

    private String topic;

    /**
     * 状态0未处理1处理2未找到对应的服务类3业务处理失败
     */
    private String state;

    // 响应消息
    private String resultMsg;

    @JsonFormat(pattern = DateUtils.DATE_TIME_MIL_PATTERN)
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getFuncCodeName() {
        return funcCodeName;
    }

    public void setFuncCodeName(String funcCodeName) {
        this.funcCodeName = funcCodeName;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
