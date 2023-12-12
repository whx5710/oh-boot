package com.iris.api.service.impl;

import cn.hutool.json.JSONObject;
import com.iris.api.entity.DataMsgEntity;
import com.iris.api.service.TaskService;
import com.iris.api.utils.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 业务一
 */
@Service
public class F1001ServiceImpl implements TaskService, InitializingBean{
    private final Logger log = LoggerFactory.getLogger(F1001ServiceImpl.class);
    @Override
    public void check(DataMsgEntity data) {
        log.info("校验数据======================业务一 F1001");
    }

    @Override
    public JSONObject handle(DataMsgEntity data) {
        log.info("处理业务一数据 F1001" + data.toJson());
        JSONObject object = new JSONObject();
        object.set("msg","ok");
        return object;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceFactory.register("F1001", this);
    }

}
