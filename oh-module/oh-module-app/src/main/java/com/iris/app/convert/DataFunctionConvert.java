package com.iris.app.convert;

import com.iris.app.entity.DataFunctionEntity;
import com.iris.app.vo.DataFunctionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
* 功能列表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-30
*/
@Mapper
public interface DataFunctionConvert {
    DataFunctionConvert INSTANCE = Mappers.getMapper(DataFunctionConvert.class);

    DataFunctionEntity convert(DataFunctionVO vo);

    DataFunctionVO convert(DataFunctionEntity entity);

    List<DataFunctionVO> convertList(List<DataFunctionEntity> list);

}