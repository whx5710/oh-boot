package com.finn.sys.base.convert;

import com.finn.sys.base.entity.SysLogOperateEntity;
import com.finn.sys.base.vo.SysLogOperateVO;
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
public interface SysLogOperateConvert {
    SysLogOperateConvert INSTANCE = Mappers.getMapper(SysLogOperateConvert.class);

    SysLogOperateEntity convert(SysLogOperateVO vo);

    SysLogOperateVO convert(SysLogOperateEntity entity);

    List<SysLogOperateVO> convertList(List<SysLogOperateEntity> list);

}