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
import com.finn.urban.mapper.SportCheckinMapper;
import com.finn.urban.mapper.SportRecordMapper;
import com.finn.urban.mapper.TrajectoryMapper;
import com.finn.urban.query.SportRecordQuery;
import com.finn.urban.service.SportRecordService;
import com.finn.urban.vo.PointVO;
import com.finn.urban.vo.SportCheckinVO;
import com.finn.urban.vo.SportRecordVO;
import com.github.pagehelper.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.finn.common.constant.Constant.ASC;
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

    private final SportCheckinMapper sportCheckinMapper;

    private final ObjectMapper objectMapper;

    public SportRecordServiceImpl(SportRecordMapper sportRecordMapper, TrajectoryMapper trajectoryMapper,
                                  RedisCache redisCache, SportCheckinMapper sportCheckinMapper,
                                  ObjectMapper objectMapper) {
        this.sportRecordMapper = sportRecordMapper;
        this.trajectoryMapper = trajectoryMapper;
        this.redisCache = redisCache;
        this.sportCheckinMapper = sportCheckinMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Page<SportRecordEntity> page(SportRecordQuery query) {
        QueryWrapper<SportRecordEntity> queryWrapper = getQueryWrapper(query);
        queryWrapper.page(query.getPageNum(), query.getPageSize()).orderBy("create_time desc");
        return sportRecordMapper.listByWrapper(queryWrapper);
    }

    @Override
    public SportRecordVO detail(Long id) {
        AssertUtils.isNull(id, "ID");
        SportRecordEntity entity = sportRecordMapper.findById(id, SportRecordEntity.class);
        if (entity == null || entity.getId() == null) {
            throw new ServerException("未找到运动记录信息");
        }
        SportRecordVO vo = SportRecordConvert.INSTANCE.convert(entity);

        // 处理坐标点
        QueryWrapper<TrajectoryEntity> queryWrapper = QueryWrapper.of(TrajectoryEntity.class);
        queryWrapper.eq(TrajectoryEntity::getDbStatus, 1).eq(TrajectoryEntity::getCreator, vo.getUserId())
                        .eq(TrajectoryEntity::getGroupId, vo.getId()).orderBy("gps_time asc");
        List<TrajectoryEntity> list = trajectoryMapper.listByWrapper(queryWrapper);
        if(list != null && !list.isEmpty()){
            List<PointVO> points = new ArrayList<>(list.size());
            for(TrajectoryEntity item: list){
                points.add(new PointVO(item.getLongitude(), item.getLatitude()));
            }
            vo.setPoints(points);
        }

        // 打卡点（按创建时间升序，photos JSON 解析回 List<String>）
        QueryWrapper<SportCheckin> cqw = QueryWrapper.of(SportCheckin.class);
        cqw.eq(SportCheckin::getGroupId, vo.getId())
                .eq(SportCheckin::getDbStatus, 1)
                .orderBy(SportCheckin::getCreatedAt, ASC);
        List<SportCheckin> checkins = sportCheckinMapper.listByWrapper(cqw);
        if (checkins != null && !checkins.isEmpty()) {
            List<SportCheckinVO> cvos = SportCheckinConvert.INSTANCE.convertList(checkins);
            for (int i = 0; i < checkins.size(); i++) {
                // cvos.get(i).setPhotos(fromJson(checkins.get(i).getPhotos()));
                cvos.get(i).setPhotos(JsonUtils.parseArray(checkins.get(i).getPhotos(), String.class));
            }
            vo.setCheckins(cvos);
        }
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
            throw new ServerException("运动已结束，无需重复结束");
        }
        // 查询轨迹坐标是否有效，小于20条定位点，不记录此次运动
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

        // 按 clientId 去重落库打卡点（兼容中途已实时保存与离线补传两种场景）
        List<SportCheckinVO> checkins = vo.getCheckins();
        if (checkins != null && !checkins.isEmpty()) {
            for (SportCheckinVO c : checkins) {
                c.setGroupId(id);
                upsertCheckin(c);
            }
        }
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

    @Override
    public Long saveCheckin(SportCheckinVO vo) {
        AssertUtils.isNull(vo.getGroupId(), "运动记录ID");
        return doInsertCheckin(vo);
    }

    @Override
    public void fillCheckinCount(List<SportRecordVO> vos) {
        if (vos == null || vos.isEmpty()) {
            return;
        }
        List<Long> ids = new ArrayList<>();
        for (SportRecordVO v : vos) {
            if (v.getId() != null) {
                ids.add(v.getId());
            }
        }
        if (ids.isEmpty()) {
            return;
        }
        QueryWrapper<SportCheckin> qw = QueryWrapper.of(SportCheckin.class);
        qw.eq(SportCheckin::getDbStatus, 1).in(SportCheckin::getGroupId, ids);
        List<SportCheckin> all = sportCheckinMapper.listByWrapper(qw);
        Map<Long, Integer> countMap = new HashMap<>();
        if (all != null) {
            for (SportCheckin c : all) {
                countMap.merge(c.getGroupId(), 1, Integer::sum);
            }
        }
        for (SportRecordVO v : vos) {
            v.setCheckinCount(countMap.getOrDefault(v.getId(), 0));
        }
    }

    // ==================== 打卡点私有方法 ====================

    /**
     * 按 clientId 去重插入打卡点；clientId 为空则直接插入
     */
    private void upsertCheckin(SportCheckinVO vo) {
        if (vo.getClientId() != null && !vo.getClientId().isEmpty()) {
            QueryWrapper<SportCheckin> qw = QueryWrapper.of(SportCheckin.class);
            qw.eq(SportCheckin::getGroupId, vo.getGroupId())
                    .eq(SportCheckin::getClientId, vo.getClientId())
                    .eq(SportCheckin::getDbStatus, 1);
            List<SportCheckin> exist = sportCheckinMapper.listByWrapper(qw);
            if (exist != null && !exist.isEmpty()) {
                return;
            }
        }
        doInsertCheckin(vo);
    }

    /**
     * 插入一个打卡点，返回后端生成的 id
     */
    private Long doInsertCheckin(SportCheckinVO vo) {
        SportCheckin entity = SportCheckinConvert.INSTANCE.convert(vo);
        // entity.setPhotos(toJson(vo.getPhotos()));
        entity.setPhotos(JsonUtils.toJsonString(vo.getPhotos()));
        LocalDateTime now = LocalDateTime.now();
        entity.setDbStatus(1);
        entity.setCreator(SecurityUser.getUserId());
        entity.setCreateTime(now);
        entity.setUpdater(SecurityUser.getUserId());
        entity.setUpdateTime(now);
        sportCheckinMapper.insert(entity);
        return entity.getId();
    }

    /**
     * photos 列表转 JSON 字符串
     */
    /*private String toJson(List<String> photos) {
        if (photos == null || photos.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(photos);
        } catch (Exception e) {
            return null;
        }
    }*/

    /**
     * JSON 字符串转 photos 列表
     */
    /*private List<String> fromJson(String photos) {
        if (photos == null || photos.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(photos, new TypeReference<List<String>>() {
            });
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }*/

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
