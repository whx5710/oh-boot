package com.iris.api.service;

import cn.hutool.json.JSONObject;
import com.iris.api.entity.DataMsgEntity;

public interface TaskService {

    // 校验参数
    void check(DataMsgEntity data);
    // 业务处理
    JSONObject handle(DataMsgEntity data);
}
