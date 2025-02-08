package com.finn.app.convert;

import com.finn.app.entity.DataMsgEntity;
import com.finn.app.vo.DataMsgVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 接口参数数据
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-03-24
 */
@Mapper
public interface DataMsgConvert {
    DataMsgConvert INSTANCE = Mappers.getMapper(DataMsgConvert.class);

    DataMsgVO convert(DataMsgEntity entity);

    List<DataMsgVO> convertList(List<DataMsgEntity> list);
}
