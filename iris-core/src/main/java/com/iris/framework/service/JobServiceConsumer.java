package com.iris.framework.service;

import com.iris.framework.cache.RedisCache;
import com.iris.framework.cache.RedisKeys;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.common.utils.Result;
import com.iris.framework.common.utils.ServiceFactory;
import com.iris.framework.entity.api.MsgEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 根据 funcCode 执行对应的服务
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-10-05
 */
@Component
public class JobServiceConsumer {

    private final Logger log = LoggerFactory.getLogger(JobServiceConsumer.class);

    private final RedisCache redisCache;

    public JobServiceConsumer(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    public void jobConsume(String message, String topic) {
        MsgEntity dataMsg = JsonUtils.parseObject(message, MsgEntity.class);
        dataMsg.setTopic(topic==null?Constant.TOPIC_SUBMIT:topic);
        Optional<JobService> optional = ServiceFactory.getService(dataMsg.getFuncCode());
        try {
            if(optional.isPresent()){
                /**
                 * 参数校验在 OpenApiController 中已进行校验过，因此此处可以不需要再调用，可直接进行业务处理，
                 * 在业务处理过程中，发生异常，可直接抛出异常，状态会记录在消息表中
                 */
                Result<?> result = optional.get().handle(dataMsg);
                dataMsg.setResultMsg(JsonUtils.toJsonString(result));
                dataMsg.setState("1");
            }else{
                log.error("未找到对应服务，处理失败！{}", dataMsg.getFuncCode());
                dataMsg.setState("2"); // 未找到对应的服务类，处理失败
                dataMsg.setNote("未找到对应的服务类，处理失败!");
            }
        }catch (Exception e){
            log.error("处理业务发生错误！{}", e.getMessage());
            dataMsg.setNote(e.getMessage());
            dataMsg.setState("3"); // 异常
        }finally {
            if(dataMsg.getResultMsg() != null){
                redisCache.leftPush(RedisKeys.getDataMsgKey(), dataMsg, 604800); // 缓存7天 60*60*24*7
            }
        }
    }
}
