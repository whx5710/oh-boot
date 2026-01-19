package com.finn.system.convert;

import com.finn.system.entity.PostEntity;
import com.finn.system.vo.PostVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface PostConvert {
    PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);

    PostVO convert(PostEntity entity);

    PostEntity convert(PostVO vo);

    List<PostVO> convertList(List<PostEntity> list);

}
