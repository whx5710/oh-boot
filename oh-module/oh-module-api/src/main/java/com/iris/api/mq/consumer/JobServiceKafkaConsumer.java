package com.iris.api.mq.consumer;

import com.iris.framework.common.constant.Constant;
import com.iris.framework.service.JobServiceConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * Kafka 消息消费，业务处理
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
@Component
public class JobServiceKafkaConsumer {

    private final JobServiceConsumer jobServiceConsumer;

    public JobServiceKafkaConsumer(JobServiceConsumer jobServiceConsumer){
        this.jobServiceConsumer = jobServiceConsumer;
    }

    /**
     * 公共接口消费消息
     * 默认不监听，可通过调用方法开启监听
     * @param message 消息
     */
    @KafkaListener(id = Constant.OPEN_API, topics = Constant.TOPIC_SUBMIT, autoStartup = "${oh.open-api.auto-start-up:false}", groupId = "open-api-group-id")
    public void openApiJobConsume(String message, Acknowledgment ack) {
        jobServiceConsumer.jobConsume(message, Constant.TOPIC_SUBMIT);
    }

}
