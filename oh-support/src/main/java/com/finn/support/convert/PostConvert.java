package com.finn.support.convert;

import com.finn.support.vo.PostVO;
import com.finn.support.entity.PostEntity;
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
