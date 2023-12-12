//package com.iris.api.module.mq;
//
//import com.alibaba.fastjson.JSON;
//import com.iris.api.entity.DataMsgEntity;
//import com.iris.api.service.DataMsgService;
//import com.iris.api.service.TaskService;
//import com.iris.api.utils.ServiceRouteFactory;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.Optional;
//
//@Component
//public class MQConsumerService {
//
//    private final Logger log = LoggerFactory.getLogger(MQConsumerService.class);
//
//    @Resource
//    DataMsgService dataMsgService;
//
//    // topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
//    // selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
//    @Service
//    @RocketMQMessageListener(topic = "OH_TOPIC_ASYNC_SEND", selectorExpression = "asyncSend", consumerGroup = "oh_group")
//    public class ConsumerSend implements RocketMQListener<DataMsgEntity> {
//        // 监听到消息就会执行此方法
//        @Override
//        public void onMessage(DataMsgEntity data) {
//            log.info("监听到消息：data={}", JSON.toJSONString(data));
//            dataMsgService.saveData(data);
//            Optional<TaskService> optional = ServiceRouteFactory.getService(data.getFunCode());
//            TaskService taskService = optional.get();
//            taskService.handle(data);
//        }
//    }
//}
