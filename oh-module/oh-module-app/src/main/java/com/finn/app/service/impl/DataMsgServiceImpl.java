package com.finn.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.finn.core.utils.*;
import com.github.pagehelper.Page;
import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.core.constant.Constant;
import com.finn.framework.common.properties.OpenApiProperties;
import com.finn.framework.utils.ServiceFactory;
import com.finn.framework.datasource.config.DynamicDataSource;
import com.finn.framework.entity.api.DataAppDTO;
import com.finn.framework.entity.api.MsgEntity;
import com.finn.core.exception.ServerException;
import com.finn.app.entity.DataMsgEntity;
import com.finn.app.mapper.DataMessageMapper;
import com.finn.app.query.DataMsgQuery;
import com.finn.app.service.DataMsgService;
import com.finn.app.vo.DataMsgVO;
import com.finn.framework.service.JobService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * mq日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-04-21
 */
@Service
public class DataMsgServiceImpl implements DataMsgService {

    private final Logger log = LoggerFactory.getLogger(DataMsgServiceImpl.class);

    private final RedisCache redisCache;
    private final DataMessageMapper dataMessageMapper;
    private final DynamicDataSource dynamicDataSource;
    private final OpenApiProperties openApiProperties;

    private final KafkaTemplate<String, String> kafkaTemplate;
    public DataMsgServiceImpl(RedisCache redisCache, DataMessageMapper dataMessageMapper,
                              DynamicDataSource dynamicDataSource, OpenApiProperties openApiProperties,
                              KafkaTemplate<String, String> kafkaTemplate){
        this.redisCache = redisCache;
        this.dataMessageMapper = dataMessageMapper;
        this.dynamicDataSource = dynamicDataSource;
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
        idList.forEach(id -> {
            DataMsgEntity param = new DataMsgEntity();
            param.setId(id);
            param.setDbStatus(0);
            dataMessageMapper.updateById(param);
        });
    }

    /**
     * 删除日期之前的数据
     * @param date date
     */
    @Override
    public void deleteByDate(String date) {
        dataMessageMapper.deleteByDate(date);
    }

    // 保存报文
    @Override
    public void saveMsgLog() {
        SqlSession sqlSession = null;
        try {
            String key = RedisKeys.getDataMsgKey();
            // 每次插入800条
            int count = 800;
            List<DataMsgEntity> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                MsgEntity msgEntity = (MsgEntity) redisCache.rightPop(key);
                if (msgEntity == null) {
                    break;
                }
                DataMsgEntity entity = BeanUtil.copyProperties(msgEntity, DataMsgEntity.class);
                entity.setJsonStr(JsonUtils.toJsonString(msgEntity.getData()));
                entity.setCreateTime(LocalDateTime.now());
                // dataMessageMapper.insertDataMsg(entity);
                list.add(entity);
            }
            if(!list.isEmpty()){
                // dynamicDataSource.getSqlSessionFactory(sysDataSourceProperties.getSysDefault()); // 系统数据源
                // SqlSessionFactory sqlSessionFactory = dynamicDataSource.getSqlSessionFactory(sysDataSourceProperties.getPrimary()); // 主数据源
                SqlSessionFactory sqlSessionFactory = dynamicDataSource.getSqlSessionFactory(); // 默认主数据源
                sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
                DataMessageMapper messageMapper = sqlSession.getMapper(DataMessageMapper.class);
                list.forEach(messageMapper::insertDataMsg);
            }
        } catch (Exception e) {
            log.error("保存消息日志发生异常：{}", ExceptionUtils.getExceptionMessage(e));
        }finally {
            if(sqlSession != null){
                sqlSession.commit();
                sqlSession.clearCache();
                sqlSession.close();// 用完关闭
            }
        }
    }

    /**
     * 校验接口基本信息
     * @param request
     * @return
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

        List<DataAppDTO> list = (List<DataAppDTO>) obj;
        MsgEntity msgEntity = new MsgEntity();
        if(!list.isEmpty()){
            DataAppDTO dataAppDTO = list.getFirst();
            if(!dataAppDTO.getSecretKey().equals(secretKey)){
                throw new ServerException("密钥错误，请求非法");
            }
            msgEntity.setAsync(dataAppDTO.getAsync()); // 接口是否支持异步
        }
        msgEntity.setClientId(clientId);
        msgEntity.setFuncCode(funcCode);
        return msgEntity;
    }

    /**
     * 调用业务
     * @param params
     * @return
     */
    @Override
    public Result<?> submit(Map<String, Object> params, MsgEntity msgEntity) {
        msgEntity.setData(params);
        Boolean isAsync = msgEntity.getAsync(); // 接口是否支持异步
        Optional<JobService> optional = ServiceFactory.getService(msgEntity.getFuncCode());
        if(optional.isPresent()){
            JobService jobService = optional.get();
            // 校验参数
            jobService.check(params);
            if (!isAsync || openApiProperties.getType() == 1) { // 1直接保存 2使用MQ异步保存
                return jobService.handle(msgEntity);
            }else{
                msgEntity.setId(Tools.snowFlakeId()); // 设置ID，如果在业务处理中有异常(jobService.handle)，可根据此ID更新消息状态
                try {
                    CompletableFuture<SendResult<String, String>> completableFuture =  kafkaTemplate.send(Constant.TOPIC_SUBMIT, JsonUtils.toJsonString(msgEntity));
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

    /**
     * 启动项目时，从Redis队列获取MQ日志并保存
     */
    @PostConstruct
    public void saveLogJob() {
        ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);
        // 每隔20秒，执行一次
        scheduledService.scheduleWithFixedDelay(this::saveMsgLog, 1, 20, TimeUnit.SECONDS);
    }

}
