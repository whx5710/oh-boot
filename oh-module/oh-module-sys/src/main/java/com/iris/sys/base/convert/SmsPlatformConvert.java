package com.iris.sys.base.convert;

import com.iris.sys.base.entity.SmsPlatformEntity;
import com.iris.sys.base.vo.SmsPlatformVO;
import com.iris.sys.sms.config.SmsConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 短信平台
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SmsPlatformConvert {
    SmsPlatformConvert INSTANCE = Mappers.getMapper(SmsPlatformConvert.class);

    SmsPlatformEntity convert(SmsPlatformVO vo);

    SmsPlatformVO convert(SmsPlatformEntity entity);

    List<SmsPlatformVO> convertList(List<SmsPlatformEntity> list);

    SmsConfig convert2(SmsPlatformEntity entity);

    List<SmsConfig> convertList2(List<SmsPlatformEntity> list);

}