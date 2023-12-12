package com.iris.workflow;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 缓存数据
 */
@Component
public class RunHandle {

    @Value("${camunda.bpm.admin-user.id}")
    String str;
    @PostConstruct
    public void run(){
        System.out.println("==============================工作流===============================");
        System.out.println(str);
    }
}
