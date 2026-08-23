package com.finn.urban.service.impl;

import com.finn.common.utils.AssertUtils;
import com.finn.common.utils.DateUtils;
import com.finn.common.utils.JsonUtils;
import com.finn.framework.cache.RedisCache;
import com.finn.framework.datasource.wrapper.CountWrapper;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import com.finn.urban.convert.SportCheckinConvert;
import com.finn.urban.convert.SportRecordConvert;
import com.finn.urban.entity.SportCheckin;
import com.finn.urban.entity.SportRecordEntity;
import com.finn.urban.entity.TrajectoryEntity;
import com.finn.urban.mapper.SportRecordMapper;
import com.finn.urban.mapper.TrajectoryMapper;
import com.finn.urban.query.SportRecordQuery;
import com.finn.urban.service.SportCheckinService;
import com.finn.urban.service.SportRecordService;
import com.finn.urban.util.TrajectorySimplifier;
import com.finn.urban.vo.PointVO;
import com.finn.urban.vo.SportCheckinVO;
import com.finn.urban.vo.SportRecordVO;
import com.github.pagehelper.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.finn.common.constant.Constant.DESC;

/**
 * 运动记录表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
@Service
public class SportRecordServiceImpl implements SportRecordService {

    private final SportRecordMapper sportRecordMapper;

    private final TrajectoryMapper trajectoryMapper;

    private final RedisCache redisCache;

    private final SportCheckinService sportCheckinService;

    public SportRecordServiceImpl(SportRecordMapper sportRecordMapper, TrajectoryMapper trajectoryMapper,
                                  RedisCache redisCache, SportCheckinService sportCheckinService) {
        this.sportRecordMapper = sportRecordMapper;
        this.trajectoryMapper = trajectoryMapper;
        this.redisCache = redisCache;
        this.sportCheckinService = sportCheckinService;
    }

    @Override
    public Page<SportRecordEntity> page(SportRecordQuery query) {
        QueryWrapper<SportRecordEntity> queryWrapper = getQueryWrapper(query);
        queryWrapper.page(query.getPageNum(), query.getPageSize()).orderBy("create_time desc");
        return sportRecordMapper.listByWrapper(queryWrapper);
    }

    private static final String DETAIL_CACHE_KEY = "sport:record:detail:";
    private static final int DETAIL_CACHE_EXPIRE = 1800;

    @Override
    public SportRecordVO detail(Long id) {
        AssertUtils.isNull(id, "ID");

        String cacheKey = DETAIL_CACHE_KEY + id;
        if (redisCache.hasKey(cacheKey)) {
            Object cached = redisCache.get(cacheKey);
            if (cached != null) {
                return JsonUtils.parseObject(cached.toString(), SportRecordVO.class);
            }
        }

        SportRecordEntity entity = sportRecordMapper.findById(id, SportRecordEntity.class);
        if (entity == null || entity.getId() == null) {
            throw new ServerException("未找到运动记录信息");
        }
        SportRecordVO vo = SportRecordConvert.INSTANCE.convert(entity);

        QueryWrapper<TrajectoryEntity> queryWrapper = QueryWrapper.of(TrajectoryEntity.class);
        queryWrapper.eq(TrajectoryEntity::getDbStatus, 1).eq(TrajectoryEntity::getCreator, vo.getUserId())
                        .eq(TrajectoryEntity::getGroupId, vo.getId()).orderBy("gps_time asc");
        List<TrajectoryEntity> list = trajectoryMapper.listByWrapper(queryWrapper);
        if(list != null && !list.isEmpty()){
            // 使用轨迹抽稀算法简化轨迹点
            List<PointVO> points = TrajectorySimplifier.simplifyEntities(list, 10.0, 300);
            vo.setPoints(points);
        }

        List<SportCheckin> checkins = sportCheckinService.listByGroupId(vo.getId());
        if (checkins != null && !checkins.isEmpty()) {
            List<SportCheckinVO> cvos = SportCheckinConvert.INSTANCE.convertList(checkins);
            for (int i = 0; i < checkins.size(); i++) {
                cvos.get(i).setPhotos(JsonUtils.parseArray(checkins.get(i).getPhotos(), String.class));
            }
            vo.setCheckins(cvos);
        }

        redisCache.set(cacheKey, JsonUtils.toJsonString(vo), DETAIL_CACHE_EXPIRE);
        return vo;
    }

    @Override
    public Long save(SportRecordVO vo) {
        SportRecordEntity entity = SportRecordConvert.INSTANCE.convert(vo);
        Long userId = SecurityUser.getUserId();
        LocalDateTime now = LocalDateTime.now();
        entity.setUserId(userId);
        entity.setDbStatus(1);
        entity.setCreator(userId);
        entity.setCreateTime(now);
        entity.setUpdater(userId);
        entity.setUpdateTime(now);
        sportRecordMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 开始运动
     * @return
     */
    @Override
    public Long start(SportRecordVO vo) {
        Long userId = SecurityUser.getUserId();
        if(userId == null){
            throw new ServerException("请先登录");
        }
        // 判断之前的运动是否已结束
        QueryWrapper<SportRecordEntity> queryWrapper = QueryWrapper.of(SportRecordEntity.class);
        queryWrapper.eq(SportRecordEntity::getDbStatus, 1).eq(SportRecordEntity::getUserId, userId)
                .isNull(SportRecordEntity::getEndTime);
        List<SportRecordEntity> list = sportRecordMapper.listByWrapper(queryWrapper);
        if(list != null && !list.isEmpty()){
            LocalDateTime now = LocalDateTime.now();
            for(SportRecordEntity item : list){
                item.setEndTime(now);
                Duration duration = Duration.between(item.getStartTime(), now);
                item.setDuration(duration.getSeconds());
                item.setRemark("启动了新的运动，结束之前的");
                sportRecordMapper.updateById(item);
            }
        }

        SportRecordEntity entity = new SportRecordEntity();
        LocalDateTime now = LocalDateTime.now();
        entity.setStartTime(now);
        entity.setRecordDate(DateUtils.format(now, "yyyyMMdd"));
        entity.setName(DateUtils.format(now, "yyyyMMddHHmmss"));
        entity.setUserId(userId);
        entity.setStartAddress(vo.getStartAddress());
        entity.setDbStatus(1);
        entity.setCreator(userId);
        entity.setCreateTime(now);
        sportRecordMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 结束运动
     * @param id
     */
    @Override
    public String finish(Long id, SportRecordVO vo) {
        AssertUtils.isNull(id, "ID");
        SportRecordEntity entity = sportRecordMapper.findById(id, SportRecordEntity.class);
        if(entity == null || entity.getId() == null){
            throw new ServerException("未找到对应的运动记录");
        }
        if(entity.getEndTime() != null){
            return "运动已结束，无需重复结束";
        }
        CountWrapper<TrajectoryEntity> countWrapper = CountWrapper.of(TrajectoryEntity.class);
        countWrapper.eq(TrajectoryEntity::getCreator, entity.getUserId()).eq(TrajectoryEntity::getGroupId, entity.getId())
                .eq(TrajectoryEntity::getDbStatus, 1);
        long num = trajectoryMapper.count(countWrapper);
        String msg = "结束成功";
        if(num < 20){
            msg = "轨迹太短，不保存";
            entity.setDbStatus(0);
            entity.setRemark(msg);
        }
        LocalDateTime now = LocalDateTime.now();
        entity.setEndTime(now);
        Duration duration = Duration.between(entity.getStartTime(), now);
        entity.setDuration(duration.getSeconds());

        entity.setEndAddress(vo.getEndAddress());
        entity.setDistance(vo.getDistance());

        sportRecordMapper.updateById(entity);

        List<SportCheckinVO> checkins = vo.getCheckins();
        if (checkins != null && !checkins.isEmpty()) {
            for (SportCheckinVO c : checkins) {
                c.setGroupId(id);
                sportCheckinService.upsertCheckin(c);
            }
        }

        redisCache.delete(DETAIL_CACHE_KEY + id);
        return msg;
    }

    /**
     * 是否正在运动期间
     * @param id
     * @return
     */
    @Override
    public Boolean isRunning(Long id) {
        AssertUtils.isNull(id, "groupId");
        SportRecordEntity entity = sportRecordMapper.findById(id, SportRecordEntity.class);
        if(entity == null || entity.getId() == null){
            return false;
        }else{
            return entity.getEndTime() == null;
        }
    }

    @Override
    public void update(SportRecordVO vo) {
        AssertUtils.isNull(vo.getId(), "ID");
        SportRecordEntity entity = SportRecordConvert.INSTANCE.convert(vo);
        entity.setUpdater(SecurityUser.getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        sportRecordMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        UpdateWrapper<SportRecordEntity> updateWrapper = UpdateWrapper.of(SportRecordEntity.class);
        updateWrapper.set(SportRecordEntity::getDbStatus, 0).in(SportRecordEntity::getId, idList);
        sportRecordMapper.updateByWrapper(updateWrapper);
    }

    /**
     * 组装查询条件
     */
    private QueryWrapper<SportRecordEntity> getQueryWrapper(SportRecordQuery query) {
        QueryWrapper<SportRecordEntity> queryWrapper = QueryWrapper.of(SportRecordEntity.class);
        queryWrapper.eq(SportRecordEntity::getDbStatus, 1)
                .eq(SportRecordEntity::getUserId, query.getUserId())
                .eq(SportRecordEntity::getRecordDate, query.getRecordDate())
                .ge(SportRecordEntity::getRecordDate, query.getStartDate())
                .le(SportRecordEntity::getRecordDate, query.getEndDate())
                .orderBy(SportRecordEntity::getRecordDate, DESC);
        if (query.getKeyWord() != null && !query.getKeyWord().isEmpty()) {
            queryWrapper.like(SportRecordEntity::getRemark, query.getKeyWord());
        }
        return queryWrapper;
    }

    /**
     * 定时清理数据
     * 1、清理轨迹不在运动时间内的数据
     * 2、结束掉未结束且无轨迹上传的运动
     */
    @Scheduled(fixedDelayString = "#{${urban.trajectory.check-interval:3600} * 1000}")
    public void cleanData() {
        String key = "urban:trajectory:clean";
        if(!redisCache.hasKey(key)){
            redisCache.set(key, "clock", 180);
            // 清理轨迹（占位参数防框架层 NPE）
            trajectoryMapper.cleanData(1);

            // 结束掉最近无轨迹的运动（12小时前启动的,近4小时无轨迹上传）
            LocalDateTime time = LocalDateTime.now();
            time = time.minusHours(12);
            // 4小时前
            LocalDateTime time4 = LocalDateTime.now();
            time4 = time4.minusHours(4);

            QueryWrapper<SportRecordEntity> queryWrapper = QueryWrapper.of(SportRecordEntity.class);
            queryWrapper.eq(SportRecordEntity::getDbStatus, 1)
                    .isNull(SportRecordEntity::getEndTime)
                    .le(SportRecordEntity::getStartTime, time);
            List<SportRecordEntity> list = sportRecordMapper.listByWrapper(queryWrapper);
            if(list != null && !list.isEmpty()){
                for(SportRecordEntity item: list){
                    QueryWrapper<TrajectoryEntity> trajectoryEntityQueryWrapper = QueryWrapper.of(TrajectoryEntity.class);
                    trajectoryEntityQueryWrapper.eq(TrajectoryEntity::getDbStatus, 1)
                            .eq(TrajectoryEntity::getGroupId, item.getId())
                            .ge(TrajectoryEntity::getGpsTimeShow, time4);
                    long l = trajectoryMapper.count(trajectoryEntityQueryWrapper);
                    // 近4小时都无轨迹，查最后的轨迹时间
                    if(l == 0){
                        QueryWrapper<TrajectoryEntity> entityQueryWrapper = QueryWrapper.of(TrajectoryEntity.class);
                        entityQueryWrapper.eq(TrajectoryEntity::getDbStatus, 1)
                                .eq(TrajectoryEntity::getGroupId, item.getId()).orderBy(TrajectoryEntity::getGpsTimeShow, DESC)
                                .page(1, 5);
                        List<TrajectoryEntity> trajectoryEntityList = trajectoryMapper.listByWrapper(entityQueryWrapper);
                        item.setRemark("无轨迹数据，自动结束");
                        if(trajectoryEntityList != null && !trajectoryEntityList.isEmpty()){
                            item.setEndTime(trajectoryEntityList.getFirst().getGpsTimeShow());
                        }else{
                            item.setEndTime(LocalDateTime.now());
                        }
                        sportRecordMapper.updateById(item);
                    }
                }
            }
            redisCache.delete(key);
        }
    }
}
