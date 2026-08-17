package com.finn.urban.convert;

import com.finn.urban.entity.TrajectoryEntity;
import com.finn.urban.vo.TrajectoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 轨迹坐标表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-17
 */
@Mapper
public interface TrajectoryConvert {

    TrajectoryConvert INSTANCE = Mappers.getMapper(TrajectoryConvert.class);

    TrajectoryEntity convert(TrajectoryVO vo);

    TrajectoryVO convert(TrajectoryEntity entity);

    List<TrajectoryVO> convertList(List<TrajectoryEntity> list);
}
