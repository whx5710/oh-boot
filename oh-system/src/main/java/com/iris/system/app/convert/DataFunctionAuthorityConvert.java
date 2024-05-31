package com.iris.system.app.convert;

import com.iris.system.app.entity.DataFunctionAuthorityEntity;
import com.iris.system.app.vo.DataFunctionAuthorityVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
* 客户端接口授权
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
@Mapper
public interface DataFunctionAuthorityConvert {
    DataFunctionAuthorityConvert INSTANCE = Mappers.getMapper(DataFunctionAuthorityConvert.class);

    DataFunctionAuthorityEntity convert(DataFunctionAuthorityVO vo);

    DataFunctionAuthorityVO convert(DataFunctionAuthorityEntity entity);

    List<DataFunctionAuthorityVO> convertList(List<DataFunctionAuthorityEntity> list);

}