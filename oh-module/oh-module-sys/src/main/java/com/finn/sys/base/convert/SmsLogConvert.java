package com.finn.sys.base.convert;

import com.finn.sys.base.entity.SmsLogEntity;
import com.finn.sys.base.vo.SmsLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 短信日志
* @since 1.0.0 2023-10-03
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SmsLogConvert {
    SmsLogConvert INSTANCE = Mappers.getMapper(SmsLogConvert.class);

    SmsLogVO convert(SmsLogEntity entity);

    List<SmsLogVO> convertList(List<SmsLogEntity> list);

}