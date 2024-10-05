package com.iris.sys.app.service.impl;

import com.iris.framework.entity.MetaEntity;
import com.iris.framework.service.JobService;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.common.utils.Result;
import com.iris.framework.common.utils.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 业务一
 */
@Service
public class F1002ServiceImpl implements JobService, InitializingBean {
    private final Logger log = LoggerFactory.getLogger(F1002ServiceImpl.class);

    @Override
    public void check(Map<String, Object> data) {
        log.info("校验数据====================== F1002 " + JsonUtils.toJsonString(data));
    }

    @Override
    public Result<String> handle(MetaEntity data) {
        log.info("处理业务二数据 F1002");
        return Result.ok("操作成功");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceFactory.register("F1002", this);
    }

}
