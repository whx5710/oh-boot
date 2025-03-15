package com.finn.sys.base.convert;

import com.finn.sys.base.entity.LogOperateEntity;
import com.finn.sys.base.vo.LogOperateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 操作日志
 * @since 1.0.0 2023-10-03
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface LogOperateConvert {
    LogOperateConvert INSTANCE = Mappers.getMapper(LogOperateConvert.class);

    LogOperateEntity convert(LogOperateVO vo);

    LogOperateVO convert(LogOperateEntity entity);

    List<LogOperateVO> convertList(List<LogOperateEntity> list);

}