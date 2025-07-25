package com.finn.app.service;

import com.finn.core.constant.Constant;
import com.finn.core.utils.JsonUtils;
import com.finn.framework.entity.api.MsgEntity;
import com.finn.framework.service.JobServiceConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
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
    // 指定使用beanname为doSomethingExecutor的线程池
    @Async("doSomethingExecutor")
    @KafkaListener(id = Constant.OPEN_API, topics = Constant.TOPIC_SUBMIT, autoStartup = "${finn.open-api.auto-start-up:false}", groupId = "open-api-group-id")
    public void openApiJobConsume(String message, Acknowledgment ack) {
        MsgEntity dataMsg = JsonUtils.parseObject(message, MsgEntity.class);
        // jobServiceConsumer.jobConsume(dataMsg, false); // 从map中获取服务类
        jobServiceConsumer.jobBeanConsume(dataMsg, false); // 根据beanName获取服务类，前提是beanName 与 功能号相同
        ack.acknowledge();
    }

}
