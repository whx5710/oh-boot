package com.finn.sys.convert;

import com.finn.framework.operatelog.dto.OperateLogDTO;
import com.finn.sys.entity.LogOperateEntity;
import com.finn.sys.vo.LogOperateVO;
import org.mapstruct.DecoratedWith;
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
@DecoratedWith(LogOperateExtConvert.class) // 指定实现类
public interface LogOperateConvert {
    LogOperateConvert INSTANCE = Mappers.getMapper(LogOperateConvert.class);

    LogOperateEntity convert(LogOperateVO vo);

    LogOperateEntity convert(OperateLogDTO vo);

    LogOperateVO convert(LogOperateEntity entity);

    List<LogOperateVO> convertList(List<LogOperateEntity> list);

}