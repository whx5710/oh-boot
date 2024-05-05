package com.iris.api.mq;

import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.entity.api.MsgEntity;
import com.iris.framework.common.service.JobService;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.common.utils.Result;
import com.iris.framework.common.utils.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 消息消费，业务处理
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
@Component
public class KafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    private final RedisCache redisCache;

    public KafkaConsumer(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    /**
     * 公共接口消费消息
     * 默认不监听，可通过调用方法开启监听
     * @param message 消息
     */
    @KafkaListener(id = Constant.OPEN_API, topics = Constant.TOPIC_SUBMIT, autoStartup = "${oh.open-api.auto-start-up:false}", groupId = "open-api-group-id")
    public void openApiJobConsume(String message, Acknowledgment ack) {
        MsgEntity dataMsg = JsonUtils.parseObject(message, MsgEntity.class);
        dataMsg.setTopic(Constant.TOPIC_SUBMIT);
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
//            dataMsg.setJsonStr(JsonUtils.toJsonString(dataMsg.getData()));
            redisCache.leftPush(RedisKeys.getDataMsgKey(), dataMsg, 60*60*48); // 缓存2天
            ack.acknowledge();
        }
    }
}
