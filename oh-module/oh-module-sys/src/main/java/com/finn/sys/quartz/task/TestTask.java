package com.finn.sys.quartz.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 测试定时任务
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class TestTask {

    private final Logger log = LoggerFactory.getLogger(TestTask.class);

    public void run(String params) throws InterruptedException {
        log.info("我是testTask.run()，参数：{}，正在被执行。", params);
        Thread.sleep(1000);
    }
}
