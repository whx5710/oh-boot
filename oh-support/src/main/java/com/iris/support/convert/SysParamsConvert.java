package com.iris.support.convert;

import com.iris.support.vo.SysParamsVO;
import com.iris.support.entity.SysParamsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface SysParamsConvert {
    SysParamsConvert INSTANCE = Mappers.getMapper(SysParamsConvert.class);

    SysParamsEntity convert(SysParamsVO vo);

    SysParamsVO convert(SysParamsEntity entity);

    List<SysParamsVO> convertList(List<SysParamsEntity> list);

}