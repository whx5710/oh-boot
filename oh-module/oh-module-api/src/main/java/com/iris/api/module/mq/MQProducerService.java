//package com.iris.api.module.mq;
//
//import com.alibaba.fastjson.JSON;
//import com.iris.api.entity.DataMsgEntity;
//import jakarta.annotation.Resource;
//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//
//@Component
//public class MQProducerService {
//
//    private final Logger log = LoggerFactory.getLogger(MQProducerService.class);
//
//    @Value("${rocketmq.producer.send-message-timeout}")
//    private Integer messageTimeOut;
//
//    // 建议正常规模项目统一用一个TOPIC
//    private static final String topic = "OH_TOPIC_ASYNC_SEND";
//
//    // 直接注入使用，用于发送消息到broker服务器
//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//
//    String tag = "asyncSend";
//
//    /**
//     * 普通发送（这里的参数对象,可以随意定义，可以发送个对象，也可以是字符串等）
//     */
//    public void send(DataMsgEntity data, String tag) {
//        if(ObjectUtils.isEmpty(tag)){
//            tag = this.tag;
//        }
//        rocketMQTemplate.convertAndSend(topic + ":" + tag, data);
////        rocketMQTemplate.send(topic + ":tag1", MessageBuilder.withPayload(data).build()); // 等价于上面一行
//    }
//
//    /**
//     *
//     * @param data d
//     */
//    public void send(DataMsgEntity data) {
//        send(data, null);
//    }
//
//    /**
//     * 发送同步消息（阻塞当前线程，等待broker响应发送结果，这样不太容易丢失消息）
//     * （msgBody也可以是对象，sendResult为返回的发送结果）
//     */
//    public SendResult sendMsg(DataMsgEntity data, String tag) {
//        if(ObjectUtils.isEmpty(tag)){
//            tag = this.tag;
//        }
//        SendResult sendResult = rocketMQTemplate.syncSend(topic + ":" + tag, MessageBuilder.withPayload(data).build());
//        log.info("【sendMsg】sendResult={}", JSON.toJSONString(sendResult));
//        return sendResult;
//    }
//
//    /**
//     * 发送异步消息（通过线程池执行发送到broker的消息任务，执行完后回调：在SendCallback中可处理相关成功失败时的逻辑）
//     * （适合对响应时间敏感的业务场景）
//     */
//    public void sendAsyncMsg(String msgBody) {
//        rocketMQTemplate.asyncSend(topic, MessageBuilder.withPayload(msgBody).build(), new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                // 处理消息发送成功逻辑
//            }
//            @Override
//            public void onException(Throwable throwable) {
//                // 处理消息发送异常逻辑
//            }
//        });
//    }
//
//    /**
//     * 发送延时消息（上面的发送同步消息，delayLevel的值就为0，因为不延时）
//     * 在start版本中 延时消息一共分为18个等级分别为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
//     */
//    public void sendDelayMsg(String msgBody, int delayLevel) {
//        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(msgBody).build(), messageTimeOut, delayLevel);
//    }
//
//    /**
//     * 发送单向消息（只负责发送消息，不等待应答，不关心发送结果，如日志）
//     */
//    public void sendOneWayMsg(String msgBody) {
//        rocketMQTemplate.sendOneWay(topic, MessageBuilder.withPayload(msgBody).build());
//    }
//
//    /**
//     * 发送带tag的消息，直接在topic后面加上":tag"
//     */
//    public SendResult sendTagMsg(String msgBody, String tag) {
//        return rocketMQTemplate.syncSend(topic + ":" + tag, MessageBuilder.withPayload(msgBody).build());
//    }
//}
