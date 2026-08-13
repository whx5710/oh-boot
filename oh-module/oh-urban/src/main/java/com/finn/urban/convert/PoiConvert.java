package com.finn.urban.convert;

import com.finn.urban.entity.Poi;
import com.finn.urban.vo.PoiVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 兴趣点
 * @since 1.0.0 2026-08-13 15:09:28
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface PoiConvert {

    PoiConvert INSTANCE = Mappers.getMapper(PoiConvert.class);

    Poi convert(PoiVO vo);

    PoiVO convert(Poi entity);

    List<PoiVO> convertList(List<Poi> list);

}
