package com.iris.system.convert;

import com.iris.system.entity.SysAttachmentEntity;
import com.iris.system.vo.SysAttachmentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface SysAttachmentConvert {
    SysAttachmentConvert INSTANCE = Mappers.getMapper(SysAttachmentConvert.class);

    SysAttachmentEntity convert(SysAttachmentVO vo);

    SysAttachmentVO convert(SysAttachmentEntity entity);

    List<SysAttachmentVO> convertList(List<SysAttachmentEntity> list);

}