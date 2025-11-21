package com.finn.sys.convert;

import com.finn.sys.entity.Sms;
import com.finn.sys.entity.SmsPlatformEntity;
import com.finn.sys.vo.SmsPlatformVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 短信平台
* @since 1.0.0 2023-10-03
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SmsPlatformConvert {
    SmsPlatformConvert INSTANCE = Mappers.getMapper(SmsPlatformConvert.class);

    SmsPlatformEntity convert(SmsPlatformVO vo);

    SmsPlatformVO convert(SmsPlatformEntity entity);

    List<SmsPlatformVO> convertList(List<SmsPlatformEntity> list);

    Sms convert2(SmsPlatformEntity entity);

    List<Sms> convertList2(List<SmsPlatformEntity> list);

}