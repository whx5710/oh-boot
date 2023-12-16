package com.iris.workflow;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 缓存数据
 */
@Component
public class RunHandle {

    @PostConstruct
    public void run(){
        System.out.println("==============================导入了工作流模块===============================");
    }
}
