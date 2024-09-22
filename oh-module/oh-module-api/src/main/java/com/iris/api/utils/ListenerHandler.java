package com.iris.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * Kafka 监听控制
 *
 * 注：每个消费者实例对象内部持有两个属性。
 * boolean running
 * boolean paused
 * 有几个改变状态的方法：
 * 调用start()方法后，running转为true
 * 调用stop()方法后，running转为false
 * 调用pause()方法后，paused转为true
 * 调用resume()方法后，paused转为false
 *
 * 只有running=true 、 paused=false 的消费者实例才能正常消费数据。
 * 注解上的autoStartup改变的是running属性。
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-03-30
 */
@Component
public class ListenerHandler {

    private final Logger log = LoggerFactory.getLogger(ListenerHandler.class);

    private final KafkaListenerEndpointRegistry registry;
    public ListenerHandler(KafkaListenerEndpointRegistry registry){
        this.registry = registry;
    }

    /**
     * 暂停监听
     * @param listenerId 监听ID
     */
    public void stop(String listenerId){
        MessageListenerContainer messageListenerContainer = this.registry.getListenerContainer(listenerId);
        messageListenerContainer.pause();
        if(messageListenerContainer.isRunning()){
            messageListenerContainer.stop();
        }
        log.info("{} 暂停监听成功！", listenerId);
    }


    /**
     * 启动监听
     * @param listenerId
     */
    public void start(String listenerId){
        MessageListenerContainer messageListenerContainer = this.registry.getListenerContainer(listenerId);
        // 判断监听容器是否启动，未启动则将其启动
        if(!messageListenerContainer.isRunning()){
            messageListenerContainer.start();
        }
        messageListenerContainer.resume();
        log.info("{} 启动监听成功！", listenerId);
    }

}
