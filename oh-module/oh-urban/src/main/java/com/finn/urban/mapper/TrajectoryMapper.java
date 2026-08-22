package com.finn.urban.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.urban.entity.TrajectoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 轨迹坐标表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-17
 */
@Mapper
public interface TrajectoryMapper extends BaseMapper<TrajectoryEntity> {

    /**
     * 定时清理运动时间外的轨迹
     * @param placeholder 占位参数，防止 SqlSession.update 传 null 时框架层 NPE；值任意（传 1 即可）
     */
    long cleanData(@Param("p") Integer placeholder);
}
