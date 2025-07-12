package com.finn.framework.service;

import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.core.constant.Constant;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.JsonUtils;
import com.finn.core.utils.Result;
import com.finn.framework.common.properties.OpenApiProperties;
import com.finn.framework.utils.ServiceFactory;
import com.finn.framework.entity.api.MsgEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 根据 funcCode 执行对应的服务
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-10-05
 */
@Component
public class JobServiceConsumer {

    private final Logger log = LoggerFactory.getLogger(JobServiceConsumer.class);

    private final RedisCache redisCache;

    private final OpenApiProperties openApiProperties;

    public JobServiceConsumer(RedisCache redisCache, OpenApiProperties openApiProperties){
        this.redisCache = redisCache;
        this.openApiProperties = openApiProperties;
    }

    /**
     * 调用JobService，执行参数验证、业务处置；服务类由spring容器进行管理，可使用注解，不存在map中，区别于jobConsume方法
     * @param dataMsg 参数对象
     * @param check 是否校验参数，未通过校验直接抛出异常
     */
    public void jobBeanConsume(MsgEntity dataMsg, Boolean check) {
        if(dataMsg == null){
            log.warn("消息对象为空!");
            return;
        }
        if(dataMsg.getTopic() == null){
            dataMsg.setTopic(Constant.TOPIC_SUBMIT);
        }
        String funcCode = dataMsg.getFuncCode();
        try {
            JobService jobService = ServiceFactory.getBean(funcCode, JobService.class);
            if(jobService == null){
                throw new ServerException("未获取到相关服务，功能号【" + funcCode + "】无效！ ");
            }
            // 在业务处理过程中，发生异常，可直接抛出异常，状态会记录在消息表中
            if(check){
                jobService.check(dataMsg.getData());
            }
            // 执行业务
            Result<?> result = jobService.handle(dataMsg);
            dataMsg.setResultMsg(JsonUtils.toJsonString(result));
            dataMsg.setState("1");
        }catch (Exception e){
            log.error("处理业务发生错误。{}: {}", funcCode, e.getMessage());
            dataMsg.setNote(e.getMessage());
            dataMsg.setState("3"); // 异常
        }finally {
            if(dataMsg.getResultMsg() != null && openApiProperties.getCacheTime() > 0){
                redisCache.leftPush(RedisKeys.getDataMsgKey(), dataMsg, openApiProperties.getCacheTime()); // 请求参数缓存7天 60*60*24*7
            }
        }
    }
}
