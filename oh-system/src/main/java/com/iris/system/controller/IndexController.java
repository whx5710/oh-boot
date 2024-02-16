package com.iris.system.controller;

import com.iris.framework.common.config.properties.ProjectProperties;
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

    private final ProjectProperties projectProperties;

    public IndexController(ProjectProperties projectProperties){
        this.projectProperties = projectProperties;
    }

    @GetMapping("/")
    public String index() {
        String msg = "后台项目已启动，祝您使用愉快！";
        msg = msg + projectProperties.getDescription();
        return msg;
    }
}
