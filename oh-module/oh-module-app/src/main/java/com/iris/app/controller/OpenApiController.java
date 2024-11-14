package com.iris.app.controller;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.iris.core.constant.Constant;
import com.iris.core.utils.JsonUtils;
import com.iris.core.utils.Result;
import com.iris.framework.entity.api.MsgEntity;
import com.iris.app.service.DataMsgService;
import com.iris.app.utils.ListenerHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
@Tag(name = "公共接口入口")
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
    @Operation(summary = "公共接口")
    @PostMapping("/submit")
    public Result<?> submit(@RequestBody Map<String, Object> params, HttpServletRequest request) {
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
        Map<String,String> head = new HashMap<>();
        head.put(Constant.CLIENT_ID,"C0001");
        head.put(Constant.SECRET_KEY,"c28a8120682d4b4fa50325ed34748e0e");
        head.put(Constant.FUNC_CODE,"F1003");
        Map<String, Object> data = new HashMap<>();
        data.put("name","王小费");
        data.put("sex","男");

        System.out.println("开始请求");

        for(int i = 0; i< 1000; i++){
            data.put("address","湖南长沙岳麓区" + System.currentTimeMillis());
            data.put("createDate", LocalDateTime.now());
            data.put("reportTime", LocalDateTime.now(ZoneId.of("+8")));
            data.put("incidentTime", LocalDateTime.now(ZoneId.systemDefault()));
            //data.put("orderCode", IdUtil.simpleUUID());
            data.put("note", "备注信息" + System.currentTimeMillis());
            String str = HttpUtil.createPost(url).addHeaders(head).body(JsonUtils.toJsonString(data)).execute().body();
            System.out.println(str);
        }
        System.out.println("结束");
    }
}
