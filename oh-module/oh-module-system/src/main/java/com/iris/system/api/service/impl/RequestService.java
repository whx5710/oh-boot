package com.iris.system.api.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.iris.system.api.entity.DataMsgEntity;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 请求HTTP
 */
@Service
public class RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    @Resource
    KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 异步缓存post请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params json参数
     * @param topic 主题
     * @return 返回空则缓存到MQ成功，否则失败
     */
    public String asyncPost(String url, Map<String,String> headers, String params, String topic){
        String result = post(url, headers, params);
        log.info("POST请求返回: {}", result);
        DataMsgEntity dataMsg = new DataMsgEntity();
        dataMsg.setJsonStr(result);
        dataMsg.setFuncCode(url);
        dataMsg.setTopic(topic);
        return mqQueue(dataMsg, topic);
    }

    /**
     * 异步缓存get请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params json参数
     * @param topic 主题
     * @return 返回空则缓存到MQ成功，否则失败
     */
    public String asyncGet(String url, Map<String,String> headers, String params, String topic){
        String result = get(url, headers, params);
        log.info("GET请求返回: {}", result);
        DataMsgEntity dataMsg = new DataMsgEntity();
        dataMsg.setJsonStr(result);
        dataMsg.setFuncCode(url);
        dataMsg.setTopic(topic);
        return mqQueue(dataMsg, topic);
    }

    /**
     * 缓存到MQ成功，否则失败
     * @param dataMsg
     * @param topic
     * @return
     */
    public String mqQueue(DataMsgEntity dataMsg, String topic){
        AtomicBoolean flag = new AtomicBoolean(false);
        CompletableFuture<SendResult<String, String>> completableFuture =  kafkaTemplate.send(topic, dataMsg.toJson());
        //执行成功回调
        completableFuture.thenAccept(msg -> {
            log.info("缓存成功");
        });
        //缓存到消息队列失败回调
        completableFuture.exceptionally(e -> {
            log.error("缓存失败", e);
            flag.set(true);
            return null;
        });
        if(flag.get()){
            return dataMsg.getJsonStr();
        }else{
            return null;
        }
    }

    /**
     * 异步缓存post请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params json参数
     * @return 返回空则缓存到MQ成功，否则失败
     */
    public String post(String url, Map<String,String> headers, String params){
        HttpRequest httpRequest = HttpUtil.createPost(url);
        if(!ObjectUtils.isEmpty(headers)){
            httpRequest.addHeaders(headers);
        }
        return httpRequest.body(params).execute().body();
    }

    /**
     * 异步缓存get请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params json参数
     * @return 返回空则缓存到MQ成功，否则失败
     */
    public String get(String url, Map<String,String> headers, String params){
        HttpRequest httpRequest = HttpUtil.createGet(url);
        if(!ObjectUtils.isEmpty(headers)){
            httpRequest.addHeaders(headers);
        }
        return httpRequest.body(params).execute().body();
    }
}
