package com.finn.support.convert;

import com.finn.support.vo.ParamsVO;
import com.finn.support.entity.ParamsEntity;
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
public interface ParamsConvert {
    ParamsConvert INSTANCE = Mappers.getMapper(ParamsConvert.class);

    ParamsEntity convert(ParamsVO vo);

    ParamsVO convert(ParamsEntity entity);

    List<ParamsVO> convertList(List<ParamsEntity> list);

}