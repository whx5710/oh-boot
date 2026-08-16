package com.finn.urban.convert;

import com.finn.urban.entity.MultiMedia;
import com.finn.urban.vo.MultiMediaVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 多媒体信息
 * @since 1.0.0 2026-06-14 17:42:45
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface MultiMediaConvert {
    MultiMediaConvert INSTANCE = Mappers.getMapper(MultiMediaConvert.class);

    MultiMedia convert(MultiMediaVO vo);

    MultiMediaVO convert(MultiMedia entity);

    List<MultiMediaVO> convertList(List<MultiMedia> list);
}
