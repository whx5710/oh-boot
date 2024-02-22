package com.iris.api.service.impl;

import cn.hutool.json.JSONObject;
import com.iris.framework.common.service.JobService;
import com.iris.framework.common.utils.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 业务一
 */
@Service
public class F1001ServiceImpl implements JobService, InitializingBean{
    private final Logger log = LoggerFactory.getLogger(F1001ServiceImpl.class);
    @Override
    public void check(JSONObject data) {
        log.info("校验数据======================业务一 F1001");
    }

    @Override
    public JSONObject handle(JSONObject data) {
        log.info("处理业务一数据 F1001" + data.toJSONString(0));
        JSONObject object = new JSONObject();
        object.set("msg","ok");
        return object;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceFactory.register("F1001", this);
    }

}
