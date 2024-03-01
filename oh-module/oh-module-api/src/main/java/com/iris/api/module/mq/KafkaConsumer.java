package com.iris.api.module.mq;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONUtil;
import com.iris.api.service.DataMsgService;
import com.iris.framework.common.entity.MetaEntity;
import com.iris.framework.common.service.JobService;
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

    private final DataMsgService dataMsgService;

    public KafkaConsumer(DataMsgService dataMsgService){
        this.dataMsgService = dataMsgService;
    }


    /**
     * 消费消息
     * @param message 消息
     */
    @KafkaListener(topics = "topic-submit", groupId = "open-api-group-id")
    public void jobConsume(String message, Acknowledgment ack) {
        try {
            MetaEntity dataMsg = JSONUtil.toBean(message, MetaEntity.class);
            dataMsg.setTopic("topic-submit");
            Optional<JobService> optional = ServiceFactory.getService(dataMsg.getFunCode());
            if(optional.isPresent()){
                // 参数校验在 OpenApiController 中已进行校验过，因此此处可以不许要再调用，可直接进行业务处理
                optional.get().handle(dataMsg.getData());
                dataMsg.setState(1);
            }else{
                log.error("未找到对应服务，处理失败！" + dataMsg.getFunCode());
                dataMsg.setState(2); // 未找到对应的服务类，处理失败
            }
            dataMsgService.saveMsg(dataMsg);
        }catch (JSONException e){
            log.error("json格式异常!" + e.getMessage());
        }finally {
            ack.acknowledge();
        }
    }
}
