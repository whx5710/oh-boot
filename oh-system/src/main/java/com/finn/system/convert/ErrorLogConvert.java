package com.finn.system.convert;

import com.finn.system.entity.ErrorLogEntity;
import com.finn.system.vo.ErrorLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统错误日志
 * @since 1.0.0 2026-04-29 18:38:22
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface ErrorLogConvert {

    ErrorLogConvert INSTANCE = Mappers.getMapper(ErrorLogConvert.class);

    ErrorLogEntity convert(ErrorLogVO vo);

    ErrorLogVO convert(ErrorLogEntity entity);

    List<ErrorLogVO> convertList(List<ErrorLogEntity> list);

}
