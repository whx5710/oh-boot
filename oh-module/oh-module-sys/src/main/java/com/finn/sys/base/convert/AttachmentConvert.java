package com.finn.sys.base.convert;

import com.finn.sys.base.entity.AttachmentEntity;
import com.finn.sys.base.vo.AttachmentVO;
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
public interface AttachmentConvert {
    AttachmentConvert INSTANCE = Mappers.getMapper(AttachmentConvert.class);

    AttachmentEntity convert(AttachmentVO vo);

    AttachmentVO convert(AttachmentEntity entity);

    List<AttachmentVO> convertList(List<AttachmentEntity> list);

}