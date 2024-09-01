package com.iris.system.base.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页 欢迎信息
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@Tag(name = "首页 欢迎信息")
public class IndexController {

    @GetMapping("/")
    @Tag(name = "首页")
    public String index() {
        return "后台项目已启动，祝您使用愉快！";
    }
}
