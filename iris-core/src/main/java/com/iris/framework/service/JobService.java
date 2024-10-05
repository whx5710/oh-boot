package com.iris.framework.service;

import com.iris.framework.entity.MetaEntity;
import com.iris.framework.common.utils.Result;

import java.util.Map;

/**
 * 任务接口，可通过 ServiceFactory 注册和获取服务操作相关业务
 * 1、校验参数；数据不合法，直接抛出异常
 * 2、处理业务；异步处理时，抛出异常，会记录消息状态，方便排查
 * @author 王小费 whx5710@qq.com
 */
public interface JobService {

    /**
     * 校验参数，如果参数非法，直接抛出异常即可
     * @param data 参数
     */
    void check(Map<String, Object> data);

    /**
     * 业务处理
     * @param data 参数
     * @return map
     */
    Result<?> handle(MetaEntity data);
}
