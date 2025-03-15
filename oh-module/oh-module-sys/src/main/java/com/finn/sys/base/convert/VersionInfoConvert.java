package com.finn.sys.base.convert;

import com.finn.sys.base.entity.VersionInfoEntity;
import com.finn.sys.base.vo.VersionInfoVO;
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
public interface VersionInfoConvert {
    VersionInfoConvert INSTANCE = Mappers.getMapper(VersionInfoConvert.class);

    VersionInfoEntity convert(VersionInfoVO vo);

    VersionInfoVO convert(VersionInfoEntity entity);

    List<VersionInfoVO> convertList(List<VersionInfoEntity> list);

}