package com.finn.urban.convert;

import com.finn.urban.entity.SportRecordEntity;
import com.finn.urban.vo.SportRecordVO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 运动记录表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
@Mapper
@DecoratedWith(SportRecordExtConvert.class) // 指定实现类
public interface SportRecordConvert {

    SportRecordConvert INSTANCE = Mappers.getMapper(SportRecordConvert.class);

    SportRecordEntity convert(SportRecordVO vo);

    SportRecordVO convert(SportRecordEntity entity);

    List<SportRecordVO> convertList(List<SportRecordEntity> list);
}
