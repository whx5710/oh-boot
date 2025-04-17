package com.finn.support.convert;

import com.finn.support.entity.LogLoginEntity;
import com.finn.support.vo.LogLoginVO;
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