package com.finn.system.convert;

import com.finn.framework.operatelog.dto.OperateLogDTO;
import com.finn.system.entity.OperateLogEntity;
import com.finn.system.vo.OperateLogVO;
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
@DecoratedWith(OperateExtLogConvert.class) // 指定实现类
public interface OperateLogConvert {
    OperateLogConvert INSTANCE = Mappers.getMapper(OperateLogConvert.class);

    OperateLogEntity convert(OperateLogVO vo);

    OperateLogEntity convert(OperateLogDTO vo);

    OperateLogVO convert(OperateLogEntity entity);

    List<OperateLogVO> convertList(List<OperateLogEntity> list);

}