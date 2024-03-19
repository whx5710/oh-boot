package com.iris.framework.common.utils;

import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务类 注册服务和获取服务
 * 注册的服务编号唯一
 * @author 王小费 whx5710@qq.com
 */
public class ServiceFactory {
    private final static Logger log = LoggerFactory.getLogger(ServiceFactory.class);
    // 保存服务类
    private static final Map<String, JobService> serviceMap = new ConcurrentHashMap<String, JobService>();

    /**
     * 根据指令，获取消息处理服务
     * @param funCode 消息指令
     * @return Optional
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
     * @param jobService 消息服务
     */
    public static void register(String funCode , JobService jobService) {
        AssertUtils.isBlank(funCode, "注册的服务不能为空");
        if(serviceMap.containsKey(funCode)){
            log.warn("服务编号【" + funCode + ":" + serviceMap.get(funCode).getClass().getName() + "】已存在，已注册 " + jobService.getClass().getName() + "，请检查！");
        }
        serviceMap.put(funCode, jobService);
    }
}
