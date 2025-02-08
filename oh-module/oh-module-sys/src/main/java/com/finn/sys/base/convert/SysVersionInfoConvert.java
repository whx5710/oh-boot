package com.finn.sys.base.convert;

import com.finn.sys.base.entity.SysVersionInfoEntity;
import com.finn.sys.base.vo.SysVersionInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 版本信息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-09-16
*/
@Mapper
public interface SysVersionInfoConvert {
    SysVersionInfoConvert INSTANCE = Mappers.getMapper(SysVersionInfoConvert.class);

    SysVersionInfoEntity convert(SysVersionInfoVO vo);

    SysVersionInfoVO convert(SysVersionInfoEntity entity);

    List<SysVersionInfoVO> convertList(List<SysVersionInfoEntity> list);

}