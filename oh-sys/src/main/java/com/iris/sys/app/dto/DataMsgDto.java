package com.iris.sys.app.dto;

import com.iris.framework.entity.IDEntity;

public class DataMsgDto extends IDEntity {

    private String msg;

    public DataMsgDto(){

    }

    public DataMsgDto(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
