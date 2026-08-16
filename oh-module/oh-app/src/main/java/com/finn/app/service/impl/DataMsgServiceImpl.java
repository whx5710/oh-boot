package com.finn.app.service.impl;

import com.finn.framework.datasource.wrapper.Wrapper;
import com.finn.common.entity.HashDto;
import com.finn.common.entity.PageResult;
import com.finn.common.entity.Result;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.common.utils.DateUtils;
import com.finn.common.utils.SnowflakeIdWorker;
import com.finn.framework.utils.ServiceFactory;
import com.github.pagehelper.Page;
import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.common.constant.Constant;
import com.finn.framework.common.properties.OpenApiProperties;
import com.finn.common.entity.DataAppDTO;
import com.finn.common.entity.MsgEntity;
import com.finn.framework.exception.ServerException;
import com.finn.app.entity.DataMsgEntity;
import com.finn.app.mapper.DataMessageMapper;
import com.finn.app.query.DataMsgQuery;
import com.finn.app.service.DataMsgService;
import com.finn.app.vo.DataMsgVO;
import com.finn.framework.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

/**
 * mq日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-04-21
 */
@Service
public class DataMsgServiceImpl implements DataMsgService {

    private final Logger log = LoggerFactory.getLogger(DataMsgServiceImpl.class);
    // ID
    private final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1,0);

    private final RedisCache redisCache;
    private final DataMessageMapper dataMessageMapper;
    private final OpenApiProperties openApiProperties;

    private final KafkaTemplate<String, String> kafkaTemplate;
    public DataMsgServiceImpl(RedisCache redisCache, DataMessageMapper dataMessageMapper,
                              OpenApiProperties openApiProperties,
                              KafkaTemplate<String, String> kafkaTemplate
    ){
        this.redisCache = redisCache;
        this.dataMessageMapper = dataMessageMapper;
        this.openApiProperties = openApiProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 分页查询
     * @param query q
     * @return p
     */
    @Override
    public PageResult<DataMsgVO> page(DataMsgQuery query) {
        Page<DataMsgVO> page = dataMessageMapper.getList(query);
        return new PageResult<>(page.getResult(), page.getTotal());
    }

    /**
     * 批量删除
     * @param idList ids
     */
    @Override
    public void delete(List<Long> idList) {
        dataMessageMapper.updateByWrapper(UpdateWrapper.of(DataMsgEntity.class)
                .set(DataMsgEntity::getDbStatus, 0).in(DataMsgEntity::getId, idList));
    }

    /**
     * 删除日期之前的数据
     * @param date date
     */
    @Override
    public void deleteByDate(String date) {
        Wrapper<DataMsgEntity> updateWrapper = UpdateWrapper.of(DataMsgEntity.class)
                .set(DataMsgEntity::getDbStatus, 0).eq(DataMsgEntity::getDbStatus, 1)
                .le(DataMsgEntity::getCreateTime, DateUtils.parseLocalDateTime(date));
        dataMessageMapper.updateByWrapper(updateWrapper);
    }

    /**
     * 校验接口基本信息
     * @param request 请求
     * @return 消息实体
     */
    @Override
    public MsgEntity basicCheck(HttpServletRequest request) {
        String clientId = request.getHeader(Constant.CLIENT_ID);
        String secretKey = request.getHeader(Constant.SECRET_KEY);
        String funcCode = request.getHeader(Constant.FUNC_CODE);
        if(clientId == null || clientId.isEmpty()){
            throw new ServerException("客户端ID不能为空，请求非法");
        }
        if(secretKey == null || secretKey.isEmpty()){
            throw new ServerException("密钥不能为空，请求非法");
        }
        if(funcCode == null || funcCode.isEmpty()){
            throw new ServerException("参数错误【功能号 "+ Constant.FUNC_CODE +" 不能为空】");
        }

        Object obj = redisCache.get(RedisKeys.getClientKey(clientId + ":" + funcCode));
        if(obj == null){
            throw new ServerException("客户端未注册或接口未授权，请求非法");
        }
        MsgEntity msgEntity = new MsgEntity();
        if(obj instanceof List<?> list){
            // List<DataAppDTO> list = (List<DataAppDTO>) obj;
            if(!list.isEmpty()){
                DataAppDTO dataAppDTO = (DataAppDTO) list.getFirst();
                if(!dataAppDTO.getSecretKey().equals(secretKey)){
                    throw new ServerException("密钥错误，请求非法");
                }
                msgEntity.setAsync(dataAppDTO.getAsync()); // 接口是否支持异步
            }
            msgEntity.setClientId(clientId);
            msgEntity.setFuncCode(funcCode);
        }

        return msgEntity;
    }

    /**
     * 调用业务
     * @param params
     * @return
     */
    @Override
    public Result<?> submit(HashDto params, MsgEntity msgEntity) {
        msgEntity.setData(params);
        Boolean isAsync = msgEntity.getAsync(); // 接口是否支持异步
        JobService jobService = ServiceFactory.getBean(msgEntity.getFuncCode(), JobService.class);
        if(jobService != null){
            // 校验参数
            jobService.check(params);
            if (!isAsync || openApiProperties.getType() == 1) { // 1直接保存 2使用MQ异步保存
                return jobService.handle(msgEntity);
            }else{
                msgEntity.setId(idWorker.nextId()); // 设置ID，如果在业务处理中有异常(jobService.handle)，可根据此ID更新消息状态
                try {
                    CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.send(Constant.TOPIC_SUBMIT, msgEntity.toJson());
                    //执行成功回调
                    completableFuture.thenAccept(msg -> {
                        log.debug("发送成功");
                    });
                    //执行失败回调
                    completableFuture.exceptionally(e -> {
                        log.error("发送失败", e);
                        throw new ServerException("发送失败！" + e.getMessage());
                    });
                }catch (KafkaException e){
                    log.error("Kafka异常（{}），切换直接调用业务接口。", e.getMessage());
                    openApiProperties.setType(1); // 1直接保存 2使用MQ异步保存
                    return jobService.handle(msgEntity);
                }
                return Result.ok("发送成功！");
            }
        }else{
            throw new ServerException("未获取到相关服务，功能号【" + msgEntity.getFuncCode() + "】无效！ ");
        }
    }

}
