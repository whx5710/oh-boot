package com.iris.framework.service;

import com.iris.core.cache.RedisCache;
import com.iris.core.cache.RedisKeys;
import com.iris.core.constant.Constant;
import com.iris.core.utils.JsonUtils;
import com.iris.core.utils.Result;
import com.iris.framework.common.properties.OpenApiProperties;
import com.iris.framework.utils.ServiceFactory;
import com.iris.framework.entity.api.MsgEntity;
import com.iris.framework.utils.SpringContextUtil;
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

    private final OpenApiProperties openApiProperties;

    public JobServiceConsumer(RedisCache redisCache, OpenApiProperties openApiProperties){
        this.redisCache = redisCache;
        this.openApiProperties = openApiProperties;
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
     * 调用JobService，执行参数验证、业务处置，结合ServiceFactory工具类使用，服务类保存在Map中
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
        String funcCode = dataMsg.getFuncCode();
        Optional<JobService> optional = ServiceFactory.getService(funcCode);
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
                log.error("未找到对应服务，处理失败！{}", funcCode);
                dataMsg.setState("2"); // 未找到对应的服务类，处理失败
                dataMsg.setNote("未找到对应的服务类，处理失败!" + funcCode);
            }
        }catch (Exception e){
            log.error("处理业务发生错误！{} {}: {}", funcCode, ServiceFactory.getServiceNote(funcCode), e.getMessage());
            dataMsg.setNote(e.getMessage());
            dataMsg.setState("3"); // 异常
        }finally {
            if(dataMsg.getResultMsg() != null && openApiProperties.getCacheTime() > 0){
                redisCache.leftPush(RedisKeys.getDataMsgKey(), dataMsg, openApiProperties.getCacheTime()); // 请求参数缓存7天 60*60*24*7
            }
        }
    }

    /**
     * 调用JobService，执行参数验证、业务处置；服务类由spring容器进行管理，可使用注解，不存在map中，区别于jobConsume方法
     * @param dataMsg 参数对象
     * @param check 是否校验参数，未通过校验直接抛出异常
     */
    public void jobBeanConsume(MsgEntity dataMsg, Boolean check) {
        if(dataMsg == null){
            log.warn("消息对象为空!");
            return;
        }
        if(dataMsg.getTopic() == null){
            dataMsg.setTopic(Constant.TOPIC_SUBMIT);
        }
        String funcCode = dataMsg.getFuncCode();
        try {
            JobService jobService = SpringContextUtil.getBean(funcCode, JobService.class);
            // 在业务处理过程中，发生异常，可直接抛出异常，状态会记录在消息表中
            if(check){
                jobService.check(dataMsg.getData());
            }
            // 执行业务
            Result<?> result = jobService.handle(dataMsg);
            dataMsg.setResultMsg(JsonUtils.toJsonString(result));
            dataMsg.setState("1");
        }catch (Exception e){
            log.error("处理业务发生错误。{} {}: {}", funcCode, ServiceFactory.getServiceNote(funcCode), e.getMessage());
            dataMsg.setNote(e.getMessage());
            dataMsg.setState("3"); // 异常
        }finally {
            if(dataMsg.getResultMsg() != null && openApiProperties.getCacheTime() > 0){
                redisCache.leftPush(RedisKeys.getDataMsgKey(), dataMsg, openApiProperties.getCacheTime()); // 请求参数缓存7天 60*60*24*7
            }
        }
    }
}
