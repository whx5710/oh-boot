package com.finn.urban.service.impl;

import com.finn.common.utils.AssertUtils;
import com.finn.common.utils.DateUtils;
import com.finn.common.utils.ExceptionUtils;
import com.finn.common.utils.NamedDaemonThreadFactory;
import com.finn.framework.cache.RedisCache;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import com.finn.urban.convert.TrajectoryConvert;
import com.finn.urban.entity.TrajectoryEntity;
import com.finn.urban.mapper.TrajectoryMapper;
import com.finn.urban.query.TrajectoryQuery;
import com.finn.urban.service.TrajectoryService;
import com.finn.urban.vo.TrajectoryVO;
import com.github.pagehelper.Page;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.finn.common.constant.Constant.DESC;
import static com.finn.common.utils.DateUtils.DATE_TIME_MIL_PATTERN;

/**
 * 轨迹坐标表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-17
 */
@Service
public class TrajectoryServiceImpl implements TrajectoryService {

    private static final Logger log = LoggerFactory.getLogger(TrajectoryServiceImpl.class);

    /**
     * Redis 队列 key
     */
    private static final String TRAJECTORY_QUEUE_KEY = "urban:trajectory:queue";

    /**
     * 每批次消费数量
     */
    private static final int BATCH_SIZE = 500;

    /**
     * 队列数据过期时间（3 天）
     */
    private static final int QUEUE_EXPIRE_SECONDS = 60 * 60 * 24 * 3;

    private final TrajectoryMapper trajectoryMapper;
    private final RedisCache redisCache;

    public TrajectoryServiceImpl(TrajectoryMapper trajectoryMapper, RedisCache redisCache) {
        this.trajectoryMapper = trajectoryMapper;
        this.redisCache = redisCache;
    }

    @Override
    public Page<TrajectoryEntity> page(TrajectoryQuery query) {
        QueryWrapper<TrajectoryEntity> queryWrapper = getQueryWrapper(query);
        queryWrapper.page(query.getPageNum(), query.getPageSize());
        return trajectoryMapper.listByWrapper(queryWrapper);
    }

    @Override
    public TrajectoryVO detail(Long id) {
        AssertUtils.isNull(id, "ID");
        TrajectoryEntity entity = trajectoryMapper.findById(id, TrajectoryEntity.class);
        if (entity == null || entity.getId() == null) {
            throw new ServerException("未找到轨迹坐标信息");
        }
        return TrajectoryConvert.INSTANCE.convert(entity);
    }

    @Override
    public void saveBatch(List<TrajectoryVO> list) {
        AssertUtils.isNull(list, "数据");
        if (list.isEmpty()) {
            return;
        }
        Long userId = SecurityUser.getUserId();
        LocalDateTime now = LocalDateTime.now();
        for (TrajectoryVO vo : list) {
            TrajectoryEntity entity = TrajectoryConvert.INSTANCE.convert(vo);
            entity.setDbStatus(1);
            entity.setCreator(userId);
            entity.setCreateTime(now);
            redisCache.leftPush(TRAJECTORY_QUEUE_KEY, entity, QUEUE_EXPIRE_SECONDS);
        }
    }

    @Override
    public void update(TrajectoryVO vo) {
        AssertUtils.isNull(vo.getId(), "ID");
        TrajectoryEntity entity = TrajectoryConvert.INSTANCE.convert(vo);
        entity.setUpdater(SecurityUser.getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        trajectoryMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        UpdateWrapper<TrajectoryEntity> updateWrapper = UpdateWrapper.of(TrajectoryEntity.class);
        updateWrapper.set(TrajectoryEntity::getDbStatus, 0).in(TrajectoryEntity::getId, idList);
        trajectoryMapper.updateByWrapper(updateWrapper);
    }

    /**
     * 组装查询条件
     */
    private QueryWrapper<TrajectoryEntity> getQueryWrapper(TrajectoryQuery query) {
        QueryWrapper<TrajectoryEntity> queryWrapper = QueryWrapper.of(TrajectoryEntity.class);
        queryWrapper.eq(TrajectoryEntity::getDbStatus, 1)
                .ge(TrajectoryEntity::getCreateTime, query.getStartTime())
                .le(TrajectoryEntity::getCreateTime, query.getEndTime())
                .orderBy(TrajectoryEntity::getCreateTime, DESC);
        if (query.getKeyWord() != null && !query.getKeyWord().isEmpty()) {
            queryWrapper.like(TrajectoryEntity::getRemark, query.getKeyWord());
        }
        return queryWrapper;
    }

    /**
     * 启动项目时，开启定时任务从 Redis 队列消费轨迹坐标并批量落库
     */
    @PostConstruct
    public void consumeTrajectoryQueue() {
        ScheduledThreadPoolExecutor scheduledService = new ScheduledThreadPoolExecutor(1, new NamedDaemonThreadFactory("trajectory-save"));
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                List<TrajectoryEntity> list = new ArrayList<>();
                for (int i = 0; i < BATCH_SIZE; i++) {
                    Object object = redisCache.rightPop(TRAJECTORY_QUEUE_KEY);
                    if (object == null) {
                        break;
                    }
                    TrajectoryEntity t = (TrajectoryEntity) object;
                    if(t.getGpsTime() != null){
                        LocalDateTime gpsTime = Instant.ofEpochMilli(t.getGpsTime()).atZone(ZoneOffset.of("+8")).toLocalDateTime();
                        t.setGpsTimeShow(DateUtils.format(gpsTime, DATE_TIME_MIL_PATTERN));
                    }
                    list.add(t);
                }
                if (!list.isEmpty()) {
                    trajectoryMapper.insertBatch(list);
                }
            } catch (Exception e) {
                log.error("保存轨迹坐标发生异常：{}", ExceptionUtils.getExceptionMessage(e));
            }
        }, 10, 45, TimeUnit.SECONDS);
    }
}
