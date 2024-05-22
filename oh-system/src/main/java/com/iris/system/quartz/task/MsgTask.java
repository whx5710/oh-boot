package com.iris.system.quartz.task;

import com.iris.system.api.service.DataMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MsgTask {
    private final Logger log = LoggerFactory.getLogger(MsgTask.class);

    private final DataMsgService dataMsgService;

    public MsgTask(DataMsgService dataMsgService){
        this.dataMsgService = dataMsgService;
    }

    public void saveMsgLog(String params) throws InterruptedException {
        log.info("保存接口报文MsgTask.saveMsgLog()，参数：{}，正在被执行", params);
        dataMsgService.saveMsgLog();
    }
}
