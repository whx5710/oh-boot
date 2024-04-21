package com.iris.api.controller;

import com.iris.api.utils.ListenerHandler;
import com.iris.framework.common.utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mq监听
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-04-21
 */
@RestController
@RequestMapping("/mq")
@Tag(name="mq监听")
public class MqController {

    @Resource
    ListenerHandler listenerHandler;

    /**
     * 启动监听
     * @param id
     * @return
     */
//    @PreAuthorize("hasAuthority('external:app:info')")
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
//    @PreAuthorize("hasAuthority('external:app:info')")
    @GetMapping("/stopListener/{id}")
    public Result<String> stopListener(@PathVariable("id")String id){
        listenerHandler.stop(id);
        return Result.ok("暂停监听");
    }
}
