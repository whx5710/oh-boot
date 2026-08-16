package com.finn.system.convert;

import com.finn.system.entity.AttachmentEntity;
import com.finn.system.vo.AttachmentVO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 附件管理
 * @since 1.0.0 2023-10-03
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
@DecoratedWith(AttachmentExtConvert.class) // 指定实现类
public interface AttachmentConvert {

    AttachmentConvert INSTANCE = Mappers.getMapper(AttachmentConvert.class);

    AttachmentEntity convert(AttachmentVO vo);

    AttachmentVO convert(AttachmentEntity entity);

    List<AttachmentVO> convertList(List<AttachmentEntity> list);

}