package com.iris.system.pim.convert;

import com.iris.system.sms.config.SmsConfig;
import com.iris.system.pim.entity.SmsPlatformEntity;
import com.iris.system.pim.vo.SmsPlatformVO;
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