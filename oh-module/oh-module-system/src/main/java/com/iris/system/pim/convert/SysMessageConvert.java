package com.iris.system.pim.convert;

import com.iris.system.pim.entity.SysMessageEntity;
import com.iris.system.pim.vo.SysMessageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 系统消息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-10-10
*/
@Mapper
public interface SysMessageConvert {
    SysMessageConvert INSTANCE = Mappers.getMapper(SysMessageConvert.class);

    SysMessageEntity convert(SysMessageVO vo);

    SysMessageVO convert(SysMessageEntity entity);

    List<SysMessageVO> convertList(List<SysMessageEntity> list);

}