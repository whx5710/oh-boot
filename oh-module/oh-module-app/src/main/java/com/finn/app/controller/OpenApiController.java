package com.finn.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finn.core.constant.Constant;
import com.finn.core.entity.HashDto;
import com.finn.core.utils.DateUtils;
import com.finn.core.utils.HttpUtil;
import com.finn.core.utils.Result;
import com.finn.framework.entity.api.MsgEntity;
import com.finn.app.service.DataMsgService;
import com.finn.app.utils.ListenerHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/***
 * 公共接口入口
 */
@RestController
@RequestMapping("/openApi")
public class OpenApiController {
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
    private final DataMsgService dataMsgService;
    private final ListenerHandler listenerHandler;

    public OpenApiController(DataMsgService dataMsgService, ListenerHandler listenerHandler){
        this.dataMsgService = dataMsgService;
        this.listenerHandler = listenerHandler;
    }

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
    @PostMapping("/submit")
    public Result<?> submit(@RequestBody HashDto params, HttpServletRequest request) {
        MsgEntity msgEntity = dataMsgService.basicCheck(request);
        return dataMsgService.submit(params, msgEntity);
    }

    /**
     * 启动监听
     * @param id
     * @return
     */
//    @PreAuthorize("hasAuthority('sys:app:info')")
    @GetMapping("/startListener/{id}")
    public Result<String> startListener(@PathVariable("id")String id){
        listenerHandler.start(id);
        return Result.ok("启动监听");
    }

    /**
     * 暂停监听
     * @param id
     * @return
     */
//    @PreAuthorize("hasAuthority('sys:app:info')")
    @GetMapping("/stopListener/{id}")
    public Result<String> stopListener(@PathVariable("id")String id){
        listenerHandler.stop(id);
        return Result.ok("暂停监听");
    }

    public static void main(String[] args) throws JsonProcessingException {
        String url = "http://localhost:8080/openApi/submit";
        LinkedMultiValueMap<String,String> head = new LinkedMultiValueMap<>();
        head.set(Constant.CLIENT_ID,"C0001");
        head.set(Constant.SECRET_KEY,"c28a8120682d4b4fa50325ed34748e0e");
        head.set(Constant.FUNC_CODE,"F1003");
        HashDto data = new HashDto();
        data.put("name","王小费");
        data.put("sex","男");

        System.out.println("开始请求");

        for(int i = 0; i< 1000; i++){
            data.put("address","湖南长沙岳麓区" + System.currentTimeMillis());

            data.put("createDate", DateUtils.format(LocalDateTime.now()));
            data.put("reportTime", DateUtils.format(LocalDateTime.now(ZoneId.of("+8"))));
            data.put("incidentTime", DateUtils.format(LocalDateTime.now(ZoneId.systemDefault())));
            //data.put("orderCode", IdUtil.simpleUUID());
            data.put("note", "备注信息" + System.currentTimeMillis());
            Map<String,String> map = new HashMap<>();
            map.put("key1","扩展参数");
            data.put("extendJsonMap", map);
            String str = HttpUtil.post(url, data, head);
            System.out.println(str);
        }
        System.out.println("结束");
    }
}
