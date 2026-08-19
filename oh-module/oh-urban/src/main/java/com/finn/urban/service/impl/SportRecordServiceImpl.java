package com.finn.urban.service.impl;

import com.finn.common.utils.AssertUtils;
import com.finn.common.utils.DateUtils;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import com.finn.urban.convert.SportRecordConvert;
import com.finn.urban.entity.SportRecordEntity;
import com.finn.urban.mapper.SportRecordMapper;
import com.finn.urban.query.SportRecordQuery;
import com.finn.urban.service.SportRecordService;
import com.finn.urban.vo.SportRecordVO;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public SportRecordServiceImpl(SportRecordMapper sportRecordMapper) {
        this.sportRecordMapper = sportRecordMapper;
    }

    @Override
    public Page<SportRecordEntity> page(SportRecordQuery query) {
        QueryWrapper<SportRecordEntity> queryWrapper = getQueryWrapper(query);
        queryWrapper.page(query.getPageNum(), query.getPageSize());
        return sportRecordMapper.listByWrapper(queryWrapper);
    }

    @Override
    public SportRecordVO detail(Long id) {
        AssertUtils.isNull(id, "ID");
        SportRecordEntity entity = sportRecordMapper.findById(id, SportRecordEntity.class);
        if (entity == null || entity.getId() == null) {
            throw new ServerException("未找到运动记录信息");
        }
        return SportRecordConvert.INSTANCE.convert(entity);
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

    @Override
    public Long start() {
        SportRecordEntity entity = new SportRecordEntity();
        Long userId = SecurityUser.getUserId();
        LocalDateTime now = LocalDateTime.now();
        entity.setStartTime(now);
        entity.setRecordDate(DateUtils.format(now, "yyyyMMdd"));
        entity.setUserId(userId);
        entity.setDbStatus(1);
        entity.setCreator(userId);
        entity.setCreateTime(now);
        sportRecordMapper.insert(entity);
        return entity.getId();
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
