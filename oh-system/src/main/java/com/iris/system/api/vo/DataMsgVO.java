package com.iris.system.api.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.utils.DateUtils;
import com.iris.system.api.entity.DataAppEntity;
import com.iris.system.api.entity.DataFunctionEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 接口参数数据
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-03-24
 */
public class DataMsgVO implements Serializable, TransPojo {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    // 数据字符串
    private String jsonStr;
    // 功能号
    @Trans(type = TransType.SIMPLE, target = DataFunctionEntity.class, fields = "name", ref = "funcCodeName", uniqueField = "funcCode", dataSource = Constant.SYS_DB)
    private String funcCode;
    // 客户端ID
    @Trans(type = TransType.SIMPLE, target = DataAppEntity.class, fields = "name", ref = "clientName", uniqueField = "clientId", dataSource = Constant.SYS_DB)
    private String clientId;

    private String clientName;

    private String funcCodeName;

    private String topic;

    // 状态0未处理1处理2未找到对应的服务类3业务处理失败
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
