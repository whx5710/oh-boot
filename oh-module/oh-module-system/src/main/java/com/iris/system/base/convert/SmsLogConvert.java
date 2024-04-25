package com.iris.system.base.convert;

import com.iris.system.base.vo.SmsLogVO;
import com.iris.system.base.entity.SmsLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 短信日志
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SmsLogConvert {
    SmsLogConvert INSTANCE = Mappers.getMapper(SmsLogConvert.class);

    SmsLogVO convert(SmsLogEntity entity);

    List<SmsLogVO> convertList(List<SmsLogEntity> list);

}