package com.finn.app.service;

import com.finn.app.convert.DataMsgConvert;
import com.finn.app.entity.DataMsgEntity;
import com.finn.app.mapper.DataMessageMapper;
import com.finn.framework.common.constant.Constant;
import com.finn.framework.common.properties.OpenApiProperties;
import com.finn.common.utils.JsonUtils;
import com.finn.framework.entity.api.MsgEntity;
import com.finn.framework.service.JobServiceConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Kafka 消息消费，业务处理
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
@Component
public class JobServiceKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(JobServiceKafkaConsumer.class);

    private final JobServiceConsumer jobServiceConsumer;
    private final OpenApiProperties openApiProperties;
    private final DataMessageMapper dataMessageMapper;

    public JobServiceKafkaConsumer(JobServiceConsumer jobServiceConsumer, OpenApiProperties openApiProperties,
                                   DataMessageMapper dataMessageMapper){
        this.jobServiceConsumer = jobServiceConsumer;
        this.openApiProperties = openApiProperties;
        this.dataMessageMapper = dataMessageMapper;
    }

    /**
     * 公共接口消费消息
     * 默认不监听，可通过调用方法开启监听
     * 保存参数和响应结果
     * @param message 消息
     */
    @KafkaListener(id = Constant.OPEN_API, topics = Constant.TOPIC_SUBMIT, autoStartup = "${finn.open-api.auto-start-up:false}", groupId = "open-api-group-id")
    public void openApiJobConsume(String message, Acknowledgment ack) {
        MsgEntity dataMsg = JsonUtils.parseObject(message, MsgEntity.class);
        // jobServiceConsumer.jobConsume(dataMsg, false); // 从map中获取服务类
        dataMsg = jobServiceConsumer.jobBeanConsume(dataMsg, false); // 根据beanName获取服务类，前提是beanName 与 功能号相同
        ack.acknowledge();

        // 处理结果保存
        if(dataMsg != null && openApiProperties.getCacheTime() > 0){
            DataMsgEntity entity = DataMsgConvert.INSTANCE.convert(dataMsg);
            entity.setJsonStr(JsonUtils.toJsonString(dataMsg.getData()));
            entity.setCreateTime(LocalDateTime.now());
            dataMessageMapper.insert(entity);
        }
    }

}
