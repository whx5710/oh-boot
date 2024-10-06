package com.iris.framework.service;

import com.iris.framework.cache.RedisCache;
import com.iris.framework.cache.RedisKeys;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.common.utils.Result;
import com.iris.framework.common.utils.ServiceFactory;
import com.iris.framework.entity.api.MsgEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 根据 funcCode 执行对应的服务
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-10-05
 */
@Component
public class JobServiceConsumer {

    private final Logger log = LoggerFactory.getLogger(JobServiceConsumer.class);

    private final RedisCache redisCache;

    public JobServiceConsumer(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    /**
     * 调用JobService，执行参数验证、业务处置
     * @param message json参数
     * @param check 是否校验参数，未通过校验直接抛出异常
     */
    public void jobConsume(String message, Boolean check) {
        MsgEntity dataMsg = JsonUtils.parseObject(message, MsgEntity.class);
        this.jobConsume(dataMsg, check);
    }

    /**
     * 调用JobService，执行参数验证、业务处置
     * @param dataMsg 参数对象
     * @param check 是否校验参数，未通过校验直接抛出异常
     */
    public void jobConsume(MsgEntity dataMsg, Boolean check) {
        if(dataMsg == null){
            log.warn("消息对象为空");
            return;
        }
        if(dataMsg.getTopic() == null){
            dataMsg.setTopic(Constant.TOPIC_SUBMIT);
        }
        Optional<JobService> optional = ServiceFactory.getService(dataMsg.getFuncCode());
        try {
            if(optional.isPresent()){
                JobService jobService = optional.get();
                // 在业务处理过程中，发生异常，可直接抛出异常，状态会记录在消息表中
                if(check){
                    jobService.check(dataMsg.getData());
                }
                // 执行业务
                Result<?> result = jobService.handle(dataMsg);
                dataMsg.setResultMsg(JsonUtils.toJsonString(result));
                dataMsg.setState("1");
            }else{
                log.error("未找到对应服务，处理失败！{}", dataMsg.getFuncCode());
                dataMsg.setState("2"); // 未找到对应的服务类，处理失败
                dataMsg.setNote("未找到对应的服务类，处理失败!");
            }
        }catch (Exception e){
            log.error("处理业务发生错误！{}", e.getMessage());
            dataMsg.setNote(e.getMessage());
            dataMsg.setState("3"); // 异常
        }finally {
            if(dataMsg.getResultMsg() != null){
                redisCache.leftPush(RedisKeys.getDataMsgKey(), dataMsg, 604800); // 请求参数缓存7天 60*60*24*7
            }
        }
    }
}
