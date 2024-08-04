package com.iris.system.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import com.iris.framework.common.entity.api.MsgEntity;
import com.iris.framework.common.utils.ExceptionUtils;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.common.utils.PageResult;
import com.iris.system.app.convert.DataMsgConvert;
import com.iris.system.app.dao.DataMessageDao;
import com.iris.system.app.entity.DataMsgEntity;
import com.iris.system.app.query.DataMsgQuery;
import com.iris.system.app.service.DataMsgService;
import com.iris.system.app.vo.DataMsgVO;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
public class DataMsgServiceImpl implements DataMsgService {

    private final Logger log = LoggerFactory.getLogger(DataMsgServiceImpl.class);

    private final RedisCache redisCache;
    private final DataMessageDao dataMessageDao;

    public DataMsgServiceImpl(RedisCache redisCache, DataMessageDao dataMessageDao){
        this.redisCache = redisCache;
        this.dataMessageDao = dataMessageDao;
    }

    /**
     * 分页查询
     * @param query q
     * @return p
     */
    @Override
    public PageResult<DataMsgVO> page(DataMsgQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<DataMsgEntity> list = dataMessageDao.getList(query);
        PageInfo<DataMsgEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(DataMsgConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
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
            dataMessageDao.updateById(param);
        });
    }

    /**
     * 删除日期之前的数据
     * @param date date
     */
    @Override
    public void deleteByDate(String date) {
        dataMessageDao.deleteByDate(date);
    }

    // 保存报文
    @Override
    public void saveMsgLog() {
        try {
            String key = RedisKeys.getDataMsgKey();
            // 每次插入100条
            int count = 100;
            for (int i = 0; i < count; i++) {
                MsgEntity msgEntity = (MsgEntity) redisCache.rightPop(key);
                if (msgEntity == null) {
                    return;
                }
                DataMsgEntity entity = BeanUtil.copyProperties(msgEntity, DataMsgEntity.class);
                entity.setJsonStr(JsonUtils.toJsonString(msgEntity.getData()));
                dataMessageDao.insertDataMsg(entity);
            }
        } catch (Exception e) {
            log.error("保存消息日志发生异常：{}", ExceptionUtils.getExceptionMessage(e));
        }
    }

    /**
     * 启动项目时，从Redis队列获取MQ日志并保存
     */
    @PostConstruct
    public void saveLogJob() {
        ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);
        // 每隔30秒，执行一次
        scheduledService.scheduleWithFixedDelay(this::saveMsgLog, 1, 30, TimeUnit.SECONDS);

    }

}
