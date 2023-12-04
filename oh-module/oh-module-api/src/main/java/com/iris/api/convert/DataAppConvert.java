package com.iris.api.convert;

import com.iris.api.entity.DataAppEntity;
import com.iris.api.vo.DataAppVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 客户端
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
@Mapper
public interface DataAppConvert {
    DataAppConvert INSTANCE = Mappers.getMapper(DataAppConvert.class);

    DataAppEntity convert(DataAppVO vo);

    DataAppVO convert(DataAppEntity entity);

    List<DataAppVO> convertList(List<DataAppEntity> list);

}