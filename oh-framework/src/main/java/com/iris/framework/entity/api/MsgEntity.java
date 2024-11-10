package com.iris.framework.entity.api;

import com.iris.framework.entity.MetaEntity;

/**
 * 消息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-29
 */
public class MsgEntity extends MetaEntity {

    private Boolean isAsync;

    private String resultMsg;

    private String note;

    private String state;

//    private String jsonStr;

    public Boolean getAsync() {
        return isAsync;
    }

    public void setAsync(Boolean async) {
        isAsync = async;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    public String getJsonStr() {
//        return jsonStr;
//    }
//
//    public void setJsonStr(String jsonStr) {
//        this.jsonStr = jsonStr;
//    }
}
