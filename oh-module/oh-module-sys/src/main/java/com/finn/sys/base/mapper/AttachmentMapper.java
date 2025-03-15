package com.finn.sys.base.mapper;

import com.finn.sys.base.entity.AttachmentEntity;
import com.finn.sys.base.query.AttachmentQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface AttachmentMapper {

    List<AttachmentEntity> getList(AttachmentQuery query);

    boolean updateById(AttachmentEntity entity);

    int insertAttach(AttachmentEntity entity);
}