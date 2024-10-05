package com.iris.api.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.iris.api.common.BaseController;
//import com.iris.api.mq.MQProducerService;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.entity.api.MsgEntity;
import com.iris.framework.exception.ServerException;
import com.iris.framework.service.JobService;
import com.iris.framework.common.utils.IrisTools;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.common.utils.Result;
import com.iris.framework.common.utils.ServiceFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
//import org.apache.rocketmq.client.producer.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/***
 * 公共接口入口
 */
@RestController
@RequestMapping("/openApi")
@Tag(name = "公共接口入口")
public class OpenApiController extends BaseController {
    /**
     * 启动Kafka命令
     * linux
     * nohup sh bin/zookeeper-server-start.sh config/zookeeper.properties &
     * nohup sh bin/kafka-server-start.sh config/server.properties &
     *
     * windows
     * bin\windows\zookeeper-server-start.bat config\zookeeper.properties
     * bin\windows\kafka-server-start.bat config\server.properties
     */

    private final Logger log = LoggerFactory.getLogger(OpenApiController.class);

    // 1直接保存 2使用MQ异步保存
    @Value("${iris.open-api.type:1}")
    private int apiType;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OpenApiController(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    /*private final MQProducerService mqProducerService;
    public OpenApiController(MQProducerService mqProducerService){
        this.mqProducerService = mqProducerService;
    }*/

    /**
     * 公共接口(Kafka)
     * 1、支持同步、异步调用
     * 2、直接调用，表中无日志记录，异步调用会记录消费数据以及业务是否处理成功（异常需抛出来才能记录）
     * 3、如果Kafka没有启动，会直接调用，不进行异步处理
     * 4、提供消费失败的查询功能（/sys/app/errLogPage接口），方便排查
     * @param params 请求参数
     * @param request 请求
     * @return 返回
     */
    @Operation(summary = "公共接口")
    @PostMapping("/submit")
    public Result<?> submit(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        MsgEntity msgEntity = this.basicCheck(request);
        msgEntity.setData(params);
        Boolean isAsync = msgEntity.getAsync(); // 接口是否支持异步

        Optional<JobService> optional = ServiceFactory.getService(msgEntity.getFuncCode());
        if(optional.isPresent()){
            JobService jobService = optional.get();
            // 校验参数
            jobService.check(params);
            if (!isAsync || apiType == 1) { // 直接业务处理
                return jobService.handle(msgEntity);
            }else{
                msgEntity.setId(IrisTools.snowFlakeId()); // 设置ID，如果在业务处理中有异常(jobService.handle)，可根据此ID更新消息状态
                try {
                    CompletableFuture<SendResult<String, String>> completableFuture =  kafkaTemplate.send(Constant.TOPIC_SUBMIT, JsonUtils.toJsonString(msgEntity));
                    //执行成功回调
                    completableFuture.thenAccept(msg -> {
                        log.debug("发送成功");
                    });
                    //执行失败回调
                    completableFuture.exceptionally(e -> {
                        log.error("发送失败", e);
                        throw new ServerException("发送失败！" + e.getMessage());
                    });
                }catch (KafkaException e){
                    log.error("Kafka异常（{}），切换直接调用业务接口。", e.getMessage());
                    apiType = 1; // 1直接保存 2使用MQ异步保存
                    return jobService.handle(msgEntity);
                }
                return Result.ok("发送成功！");
            }
        }else{
            throw new ServerException("未获取到相关服务，功能号【" + msgEntity.getFuncCode() + "】无效！ ");
        }
    }

    /**
     * 公共接口(rocketmq)
     * 1、支持同步、异步调用
     * 2、直接调用，表中无日志记录，异步调用会记录消费数据以及业务是否处理成功（异常需抛出来才能记录）
     * 3、提供消费失败的查询功能（/sys/app/errLogPage接口），方便排查
     *
     */
    /*@Operation(summary = "公共接口")
    @PostMapping("/submit")
    public Result<?> submit(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        MsgEntity msgEntity = this.basicCheck(request);
        msgEntity.setData(params);
        Boolean isAsync = msgEntity.getAsync(); // 接口是否支持异步

        Optional<JobService> optional = ServiceFactory.getService(msgEntity.getFuncCode());
        if(optional.isPresent()){
            JobService jobService = optional.get();
            // 校验参数
            jobService.check(params);
            if (!isAsync || apiType == 1) { // 直接业务处理
                return jobService.handle(msgEntity);
            }else{
                msgEntity.setId(IrisTools.snowFlakeId()); // 设置ID，如果在业务处理中有异常(jobService.handle)，可根据此ID更新消息状态
                try {
                    SendResult sendResult = mqProducerService.sendMsg(msgEntity, "asyncSend");
                    log.info("返回信息 {}", JsonUtils.toJsonString(sendResult));
                }catch (Exception e){
                    log.error("消息发送异常（{}），切换直接调用业务接口。", e.getMessage());
                    apiType = 1; // 1直接保存 2使用MQ异步保存
                    return jobService.handle(msgEntity);
                }
                return Result.ok("发送成功！");
            }
        }else{
            throw new ServerException("未获取到相关服务，功能号【" + msgEntity.getFuncCode() + "】无效！ ");
        }
    }*/

    public static void main(String[] args) throws JsonProcessingException {
        String url = "http://localhost:8080/openApi/submit";
        Map<String,String> head = new HashMap<>();
        head.put(Constant.CLIENT_ID,"C0001");
        head.put(Constant.SECRET_KEY,"c28a8120682d4b4fa50325ed34748e0e");
        head.put(Constant.FUNC_CODE,"F1003");
        Map<String, Object> data = new HashMap<>();
        data.put("name","王小费");
        data.put("sex","男");

        System.out.println("开始请求");

        for(int i = 0; i< 999; i++){
            data.put("address","湖南长沙岳麓区" + System.currentTimeMillis());
            data.put("createDate", new Date());
            data.put("reportTime", new Date());
            data.put("incidentTime", new Date());
            data.put("orderCode", IdUtil.simpleUUID());
            data.put("note", System.currentTimeMillis());
            String str = HttpUtil.createPost(url).addHeaders(head).body(JsonUtils.toJsonString(data)).execute().body();
            System.out.println(str);
        }
        System.out.println("结束");
    }
}
