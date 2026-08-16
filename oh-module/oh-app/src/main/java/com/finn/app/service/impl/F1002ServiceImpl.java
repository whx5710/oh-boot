package com.finn.app.service.impl;

import com.finn.common.entity.HashDto;
import com.finn.common.entity.MetaEntity;
import com.finn.common.utils.JsonUtils;
import com.finn.common.entity.Result;
import com.finn.framework.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务一
 */
@Service("F1002")
public class F1002ServiceImpl implements JobService {
    private final Logger log = LoggerFactory.getLogger(F1002ServiceImpl.class);

    @Override
    public void check(HashDto data) {
        log.info("校验数据====================== F1002 " + JsonUtils.toJsonString(data));
    }

    @Override
    public Result<String> handle(MetaEntity data) {
        log.info("处理业务二数据 F1002");
        return Result.ok("操作成功");
    }

}
