package com.finn.urban.convert;

import com.finn.urban.entity.SportCheckin;
import com.finn.urban.vo.SportCheckinVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 运动打卡点
 * photos 字段为 JSON 字符串(List<String> ↔ String)，在 Service 层手动转换，此处忽略
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-21
 */
@Mapper
public interface SportCheckinConvert {

    SportCheckinConvert INSTANCE = Mappers.getMapper(SportCheckinConvert.class);

    @Mapping(target = "photos", ignore = true)
    SportCheckin convert(SportCheckinVO vo);

    @Mapping(target = "photos", ignore = true)
    SportCheckinVO convert(SportCheckin entity);

    @Mapping(target = "photos", ignore = true)
    List<SportCheckinVO> convertList(List<SportCheckin> list);
}
