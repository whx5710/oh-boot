package com.iris.system.base.convert;

import com.iris.system.base.vo.SysLogLoginVO;
import com.iris.system.base.entity.SysLogLoginEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 登录日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface SysLogLoginConvert {
    SysLogLoginConvert INSTANCE = Mappers.getMapper(SysLogLoginConvert.class);

    SysLogLoginEntity convert(SysLogLoginVO vo);

    SysLogLoginVO convert(SysLogLoginEntity entity);

    List<SysLogLoginVO> convertList(List<SysLogLoginEntity> list);

}