package com.iris.system.base.controller;

import com.iris.framework.common.config.properties.ProjectProperties;
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

    private final ProjectProperties projectProperties;

    public IndexController(ProjectProperties projectProperties){
        this.projectProperties = projectProperties;
    }

    @GetMapping("/")
    @Tag(name = "首页")
    public String index() {
        String msg = "后台项目已启动，祝您使用愉快！";
        msg = msg + projectProperties.getDescription();
        return msg;
    }
}
