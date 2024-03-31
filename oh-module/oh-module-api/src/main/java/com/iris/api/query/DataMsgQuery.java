package com.iris.api.query;

import com.iris.framework.common.query.Query;

/**
 * 消息日志查询
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
public class DataMsgQuery extends Query {
    String keyWord;

    String startDate;

    String endDate;

    private Long id;

    // 数据字符串
    private String jsonStr;
    // 功能号
    private String funcCode;
    // 客户端ID
    private String clientId;
    // topic
    private String topic;

    // 状态0未处理1处理2未找到对应的服务类3业务处理失败
    private String state;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
