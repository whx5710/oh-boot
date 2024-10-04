package com.iris.sys.base.controller;

import com.iris.framework.common.utils.DateUtils;
import com.iris.framework.common.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 首页 欢迎信息
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@Tag(name = "首页")
public class IndexController {

    @GetMapping("/")
    @Operation(summary = "首页欢迎信息")
    public Result<String> index() {
        String msg = "后台项目已启动，祝您使用愉快！" + DateUtils.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
        return Result.ok(msg);
    }
}
