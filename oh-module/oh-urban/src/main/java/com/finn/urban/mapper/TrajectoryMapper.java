package com.finn.urban.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.urban.entity.TrajectoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 轨迹坐标表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-17
 */
@Mapper
public interface TrajectoryMapper extends BaseMapper<TrajectoryEntity> {

    long cleanData();
}
