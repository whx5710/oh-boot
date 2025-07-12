package com.finn.sys.base.controller;

import com.finn.core.utils.DateUtils;
import com.finn.core.utils.Result;
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
public class IndexController {

    /**
     * 首页欢迎信息
     * @return
     */
    @GetMapping("/")
    public Result<String> index() {
        String msg = "后台项目已启动，祝您使用愉快！" + DateUtils.format(LocalDateTime.now());
        return Result.ok(msg);
    }
}
