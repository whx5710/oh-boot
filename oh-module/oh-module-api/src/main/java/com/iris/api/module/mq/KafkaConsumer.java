package com.iris.api.module.mq;

import cn.hutool.json.JSONUtil;
import com.iris.api.entity.DataMsgEntity;
import com.iris.api.service.DataMsgService;
import com.iris.framework.common.service.JobService;
import com.iris.framework.common.utils.ServiceFactory;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 消息消费，业务处理
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
@Component
public class KafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @Service
    public class ConsumerSend {

        @Resource
        DataMsgService dataMsgService;

        // 监听到消息就会执行此方法
        @KafkaListener(topics = "asyncSend", groupId = "open-api-group-id")
        public void consume(String message) {
            log.debug("消费数据: " + message);
            DataMsgEntity dataMsg = JSONUtil.toBean(message, DataMsgEntity.class);
            dataMsg.setTopic("asyncSend");
            dataMsg = dataMsgService.saveData(dataMsg);
            Optional<JobService> optional = ServiceFactory.getService(dataMsg.getFunCode());
            if(optional.isPresent()){
                JobService taskService = optional.get();
                // 参数校验在 OpenApiController 中已进行校验过，因此此处可以不许要再调用，可直接进行业务处理
                taskService.handle(dataMsg.getJsonObj());
                dataMsg.setState("1");
                dataMsgService.updateById(dataMsg);
            }else{
                log.error("未找到对应服务，处理失败！" + dataMsg.getFunCode());
            }
        }
    }
}
