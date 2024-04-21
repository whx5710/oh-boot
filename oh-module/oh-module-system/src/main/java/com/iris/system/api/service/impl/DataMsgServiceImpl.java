package com.iris.system.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import com.iris.framework.common.entity.api.MsgEntity;
import com.iris.framework.common.utils.ExceptionUtils;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.impl.BaseServiceImpl;
import com.iris.system.api.convert.DataMsgConvert;
import com.iris.system.api.dao.DataMessageDao;
import com.iris.system.api.entity.DataMsgEntity;
import com.iris.system.api.query.DataMsgQuery;
import com.iris.system.api.service.DataMsgService;
import com.iris.system.api.vo.DataMsgVO;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * mq日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-04-21
 */
@Service
public class DataMsgServiceImpl extends BaseServiceImpl<DataMessageDao, DataMsgEntity> implements DataMsgService {

    private final Logger log = LoggerFactory.getLogger(DataMsgServiceImpl.class);

    private final RedisCache redisCache;

    public DataMsgServiceImpl(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    /**
     * 分页查询
     * @param query q
     * @return p
     */
    @Override
    public PageResult<DataMsgVO> page(DataMsgQuery query) {
        LambdaQueryWrapper<DataMsgEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DataMsgEntity::getDbStatus, 1);
        if(!ObjectUtils.isEmpty(query.getClientId())){
            wrapper.eq(DataMsgEntity::getClientId, query.getClientId()); // 客户端ID
        }
        if(!ObjectUtils.isEmpty(query.getState())){
            wrapper.eq(DataMsgEntity::getState, query.getState()); // 状态0未处理1处理2未找到对应的服务类3业务处理失败
        }
        if(!ObjectUtils.isEmpty(query.getFuncCode())){
            wrapper.like(DataMsgEntity::getFuncCode, query.getFuncCode()); // 功能号
        }
        if(!ObjectUtils.isEmpty(query.getTopic())){
            wrapper.like(DataMsgEntity::getTopic, query.getTopic()); // topic
        }
        if(!ObjectUtils.isEmpty(query.getStartDate())){
            wrapper.ge(DataMsgEntity::getCreateTime, query.getStartDate());
        }
        if(!ObjectUtils.isEmpty(query.getEndDate())){
            wrapper.le(DataMsgEntity::getCreateTime, query.getEndDate());
        }
        // 搜索功能号、客户端
        if(!ObjectUtils.isEmpty(query.getKeyWord())){
            wrapper.and(w -> w.like(DataMsgEntity::getFuncCode, query.getKeyWord())
                    .or().like(DataMsgEntity::getClientId, query.getKeyWord()));
        }
        wrapper.orderByDesc(DataMsgEntity::getCreateTime);
        IPage<DataMsgEntity> page = baseMapper.selectPage(getPage(query), wrapper);
        return new PageResult<>(DataMsgConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 批量删除
     * @param idList ids
     */
    @Override
    public void delete(List<Long> idList) {
        this.removeByIds(idList);
    }

    /**
     * 删除日期之前的数据
     * @param date date
     */
    @Override
    public void deleteByDate(String date) {
        LambdaUpdateWrapper<DataMsgEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.le(DataMsgEntity::getCreateTime, date) // DateUtils.parseLocalDateTime(date)
                .eq(DataMsgEntity::getDbStatus, 1);
        DataMsgEntity dataMsgEntity = new DataMsgEntity();
        dataMsgEntity.setDbStatus(0); // 删除标志
        this.baseMapper.update(dataMsgEntity, updateWrapper);
    }

    /**
     * 启动项目时，从Redis队列获取MQ日志并保存
     */
    @PostConstruct
    public void saveMsgLog() {
        ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);
        // 每隔10秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                String key = RedisKeys.getDataMsgKey();
                // 每次插入20条
                int count = 20;
                for (int i = 0; i < count; i++) {
                    MsgEntity msgEntity = (MsgEntity) redisCache.rightPop(key);
                    if (msgEntity == null) {
                        return;
                    }
                    DataMsgEntity entity = BeanUtil.copyProperties(msgEntity, DataMsgEntity.class);
                    entity.setJsonStr(JsonUtils.toJsonString(msgEntity.getData()));
                    baseMapper.insert(entity);
                }
            } catch (Exception e) {
                log.error("保存消息日志发生异常：{}", ExceptionUtils.getExceptionMessage(e));
            }
        }, 1, 10, TimeUnit.SECONDS);

    }

}
