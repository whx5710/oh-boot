package com.finn.sys.base.mapper;

import com.finn.sys.base.entity.SysAttachmentEntity;
import com.finn.sys.base.query.SysAttachmentQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface SysAttachmentMapper {

    List<SysAttachmentEntity> getList(SysAttachmentQuery query);

    boolean updateById(SysAttachmentEntity entity);

    int insertAttach(SysAttachmentEntity entity);
}