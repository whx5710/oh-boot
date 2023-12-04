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
public class F1002ServiceImpl implements TaskService, InitializingBean {
    private final Logger log = LoggerFactory.getLogger(F1002ServiceImpl.class);
    @Override
    public void check(DataMsgEntity data) {
        log.info("校验数据====================== F1002" + data.toJson());
    }

    @Override
    public JSONObject handle(DataMsgEntity data) {
        log.info("处理业务二数据 F1002");
        JSONObject object = new JSONObject();
        object.set("msg","操作成功");
        return object;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceFactory.register("F1002", this);
    }

}
