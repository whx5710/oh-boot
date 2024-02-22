package com.iris.framework.common.utils;

import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.service.JobService;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务类
 */
public class ServiceFactory {
    // 保存服务类
    private static final Map<String, JobService> serviceMap = new ConcurrentHashMap<String, JobService>();

    /**
     * 根据指令，获取消息处理服务
     * @param funCode 消息指令
     * @return
     * @throws Exception
     */
    public static Optional<JobService> getService(String funCode){
        Optional<JobService> optionalService = Optional.ofNullable(serviceMap.get(funCode));
        if(optionalService.isPresent()){
            return optionalService;
        }else{
            throw new ServerException("找不到服务【"+funCode+"】");
        }
    }

    /**
     * 注册消息处理服务
     * @param funCode 指令
     * @param messageService 消息服务
     */
    public static void register(String funCode , JobService messageService) {
        Assert.notNull(funCode, "注册的服务不能为空");
        serviceMap.put(funCode, messageService);
    }
}
