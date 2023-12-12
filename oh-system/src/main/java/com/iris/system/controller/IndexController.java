package com.iris.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页 欢迎信息
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "您好，后台项目已启动，祝您使用愉快！";
    }
}
