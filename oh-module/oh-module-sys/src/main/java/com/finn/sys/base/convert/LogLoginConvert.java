package com.finn.sys.base.convert;

import com.finn.sys.base.entity.LogLoginEntity;
import com.finn.sys.base.vo.LogLoginVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 登录日志
 * @since 1.0.0 2023-10-03
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface LogLoginConvert {
    LogLoginConvert INSTANCE = Mappers.getMapper(LogLoginConvert.class);

    LogLoginEntity convert(LogLoginVO vo);

    LogLoginVO convert(LogLoginEntity entity);

    List<LogLoginVO> convertList(List<LogLoginEntity> list);

}