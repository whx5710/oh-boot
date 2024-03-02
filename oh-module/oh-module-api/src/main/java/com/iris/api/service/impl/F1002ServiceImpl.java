package com.iris.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iris.framework.common.service.JobService;
import com.iris.framework.common.utils.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务一
 */
@Service
public class F1002ServiceImpl implements JobService, InitializingBean {
    private final Logger log = LoggerFactory.getLogger(F1002ServiceImpl.class);

    private final ObjectMapper objectMapper;

    public F1002ServiceImpl(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    @Override
    public void check(Map<String, Object> data) {
        try {
            log.info("校验数据====================== F1002" + objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> handle(Map<String, Object> data) {
        log.info("处理业务二数据 F1002");
        Map<String, Object> object = new HashMap<>();
        object.put("msg","操作成功");
        return object;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceFactory.register("F1002", this);
    }

}
