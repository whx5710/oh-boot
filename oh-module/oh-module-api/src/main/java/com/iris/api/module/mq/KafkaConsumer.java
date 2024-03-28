package com.iris.api.module.mq;

import cn.hutool.core.bean.BeanUtil;
import com.iris.api.constant.ConstantApi;
import com.iris.api.entity.DataMsgEntity;
import com.iris.api.service.DataMsgService;
import com.iris.framework.common.entity.MetaEntity;
import com.iris.framework.common.service.JobService;
import com.iris.framework.common.utils.JsonUtils;
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
    @KafkaListener(topics = ConstantApi.TOPIC_SUBMIT, groupId = "open-api-group-id")
    public void jobConsume(String message, Acknowledgment ack) {
        MetaEntity dataMsg = JsonUtils.parseObject(message, MetaEntity.class);
        dataMsg.setTopic(ConstantApi.TOPIC_SUBMIT);
        Optional<JobService> optional = ServiceFactory.getService(dataMsg.getFuncCode());
        DataMsgEntity dataMsgEntity = new DataMsgEntity();
        BeanUtil.copyProperties(dataMsg, dataMsgEntity);
        try {
            if(optional.isPresent()){
                /**
                 * 参数校验在 OpenApiController 中已进行校验过，因此此处可以不需要再调用，可直接进行业务处理，
                 * 在业务处理过程中，发生异常，可直接抛出异常，状态会记录在消息表中
                 */
                optional.get().handle(dataMsg);
                dataMsgEntity.setState("1");
            }else{
                log.error("未找到对应服务，处理失败！" + dataMsg.getFuncCode());
                dataMsgEntity.setState("2"); // 未找到对应的服务类，处理失败
                dataMsgEntity.setNote("未找到对应的服务类，处理失败!");
            }
        }catch (Exception e){
            log.error("处理业务发生错误！{}", e.getMessage());
            dataMsgEntity.setNote(e.getMessage());
            dataMsgEntity.setState("3");
        }finally {
            dataMsgEntity.setJsonStr(JsonUtils.toJsonString(dataMsg.getData()));
            dataMsgService.save(dataMsgEntity);
            ack.acknowledge();
        }
    }
}
