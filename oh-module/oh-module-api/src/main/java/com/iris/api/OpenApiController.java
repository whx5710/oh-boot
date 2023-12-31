package com.iris.api;

import cn.hutool.json.JSONObject;
import com.iris.api.common.BaseController;
import com.iris.api.entity.DataMsgEntity;
import com.iris.api.service.TaskService;
import com.iris.api.utils.ServiceFactory;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/openApi")
public class OpenApiController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(OpenApiController.class);

    // 1直接保存 2使用MQ异步保存
    @Value("${oh.open-api.type:1}")
    private int apiType;

    @Resource
    KafkaTemplate<String, String> kafkaTemplate;


    /**
     * 接收数据
     * @param jsonObj 请求参数
     * @param request 请求
     * @return 返回
     */
    @PostMapping("/send")
    public Result<JSONObject> asyncSend(@RequestBody JSONObject jsonObj, HttpServletRequest request) {
        JSONObject object = this.checkData(request);
        DataMsgEntity data = object.getBean("data", DataMsgEntity.class);
        data.setJsonObj(jsonObj);
        Boolean isAsync = object.getBool("isAsync"); // 接口是否支持异步

        JSONObject result = new JSONObject();
        result.set("msg","发送成功");
        Optional<TaskService> optional = ServiceFactory.getService(data.getFunCode());
        if(optional.isPresent()){
            TaskService taskService = optional.get();
            taskService.check(data);
            if (!isAsync || apiType == 1) { // 直接业务处理
                result = taskService.handle(data);
            }else{
                data.setState("0"); // 状态0未处理1处理
                CompletableFuture<SendResult<String, String>> completableFuture =  kafkaTemplate.send("asyncSend", data.toJson());
                //执行成功回调
                completableFuture.thenAccept(msg -> {
                    log.info("发送成功");
                });
                //执行失败回调
                completableFuture.exceptionally(e -> {
                    log.error("发送失败", e);
                    throw new ServerException("发送失败！");
                });
            }
        }else{
            throw new ServerException("未获取到相关服务，功能号【" + data.getFunCode() + "】无效！ ");
        }
        return Result.ok(result);
    }

    /*public static void main(String[] args) {
        String url = "http://localhost:8080/openApi/send";
        Map<String,String> head = new HashMap<>();
        head.put("OH-CLIENT-ID","C0001");
        head.put("OH-SECRET-KEY","c28a8120682d4b4fa50325ed34748e0e");
        head.put("OH-FUNC-CODE","F1001");
        JSONObject data = new JSONObject();
        data.set("name","王小费");
        data.set("address","湖南长沙");
        data.set("sex","name");
        System.out.println("开始请求");
        for(int i = 0; i< 99999; i++){
            data.set("createDate", new Date());
            String str = HttpUtil.createPost(url).addHeaders(head).body(data.toJSONString(0)).execute().body();
            System.out.println(str);
        }
        System.out.println("结束");
    }*/
}
