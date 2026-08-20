package com.finn.urban.service.impl;

import com.finn.common.utils.AssertUtils;
import com.finn.common.utils.DateUtils;
import com.finn.framework.datasource.wrapper.CountWrapper;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import com.finn.urban.convert.SportRecordConvert;
import com.finn.urban.entity.SportRecordEntity;
import com.finn.urban.entity.TrajectoryEntity;
import com.finn.urban.mapper.SportRecordMapper;
import com.finn.urban.mapper.TrajectoryMapper;
import com.finn.urban.query.SportRecordQuery;
import com.finn.urban.service.SportRecordService;
import com.finn.urban.vo.PointVO;
import com.finn.urban.vo.SportRecordVO;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public SportRecordServiceImpl(SportRecordMapper sportRecordMapper, TrajectoryMapper trajectoryMapper) {
        this.sportRecordMapper = sportRecordMapper;
        this.trajectoryMapper = trajectoryMapper;
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
        }
        LocalDateTime now = LocalDateTime.now();
        entity.setEndTime(now);
        Duration duration = Duration.between(entity.getStartTime(), now);
        entity.setDuration(duration.getSeconds());

        entity.setEndAddress(vo.getEndAddress());
        entity.setDistance(vo.getDistance());

        sportRecordMapper.updateById(entity);
        return msg;
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
}
