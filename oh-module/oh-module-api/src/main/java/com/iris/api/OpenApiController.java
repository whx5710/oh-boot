package com.iris.api;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.iris.api.common.BaseController;
import com.iris.api.vo.MsgVO;
import com.iris.framework.common.entity.MetaEntity;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.service.JobService;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.common.utils.Result;
import com.iris.framework.common.utils.ServiceFactory;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${oh.open-api.type:1}")
    private int apiType;

    @Resource
    KafkaTemplate<String, String> kafkaTemplate;


    /**
     * 接收数据
     * @param params 请求参数
     * @param request 请求
     * @return 返回
     */
    @PostMapping("/submit")
    public Result<Map<String, Object>> submit(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        MsgVO msgVO = this.basicCheck(request);
        MetaEntity data = msgVO.getMetaEntity();
        data.setData(params);
        Boolean isAsync = msgVO.getAsync(); // 接口是否支持异步

        Map<String, Object> result = new HashMap<>();
        result.put("msg","发送成功");
        Optional<JobService> optional = ServiceFactory.getService(data.getFunCode());
        if(optional.isPresent()){
            JobService jobService = optional.get();
            // 校验参数
            jobService.check(params);
            if (!isAsync || apiType == 1) { // 直接业务处理
                result = jobService.handle(params);
            }else{
                data.setState(0); // 状态0未处理1处理
                CompletableFuture<SendResult<String, String>> completableFuture =  kafkaTemplate.send("topic-submit", data.toJson());
                //执行成功回调
                completableFuture.thenAccept(msg -> {
                    log.debug("发送成功");
                });
                //执行失败回调
                completableFuture.exceptionally(e -> {
                    log.error("发送失败", e);
                    throw new ServerException("发送失败！" + e.getMessage());
                });
            }
        }else{
            throw new ServerException("未获取到相关服务，功能号【" + data.getFunCode() + "】无效！ ");
        }
        return Result.ok(result);
    }

    public static void main(String[] args) throws JsonProcessingException {
        String url = "http://localhost:8080/openApi/submit";
        Map<String,String> head = new HashMap<>();
        head.put("OH-CLIENT-ID","C0001");
        head.put("OH-SECRET-KEY","c28a8120682d4b4fa50325ed34748e0e");
        head.put("OH-FUNC-CODE","F1003");
        Map<String, Object> data = new HashMap<>();
        data.put("name","王小费");
        data.put("sex","name");

        System.out.println("开始请求");

        for(int i = 0; i< 99999; i++){
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
