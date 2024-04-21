package com.iris.system.api.convert;

import com.iris.framework.common.entity.api.DataAppDTO;
import com.iris.system.api.entity.DataAppEntity;
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

    DataAppEntity convert(DataAppDTO vo);

    DataAppDTO convert(DataAppEntity entity);

    List<DataAppDTO> convertList(List<DataAppEntity> list);

}