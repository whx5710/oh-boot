package com.iris.api.mq;

import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.entity.api.MsgEntity;
import com.iris.framework.common.service.JobService;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.common.utils.Result;
import com.iris.framework.common.utils.ServiceFactory;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * start mqnamesrv.cmd
 * start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true
 *
 */
@Component
public class RocketMqConsumer {
    private final Logger log = LoggerFactory.getLogger(RocketMqConsumer.class);

    private final RedisCache redisCache;

    public RocketMqConsumer(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    // topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
    // selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
    @Service
    @RocketMQMessageListener(topic = Constant.TOPIC_SUBMIT, selectorExpression = "asyncSend", consumerGroup = "oh_group")
    public class ConsumerSend implements RocketMQListener<MsgEntity> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage(MsgEntity dataMsg) {
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
                String errorMsg = e.getMessage();
                dataMsg.setNote(errorMsg.length()>500?errorMsg.substring(0,500):errorMsg);
                dataMsg.setState("3"); // 异常
            }finally {
                redisCache.leftPush(RedisKeys.getDataMsgKey(), dataMsg, 604800); // 缓存7天 60*60*24*7
            }
        }
    }
}
