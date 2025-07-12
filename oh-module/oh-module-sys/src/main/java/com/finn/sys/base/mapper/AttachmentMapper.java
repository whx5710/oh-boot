package com.finn.sys.base.mapper;

import com.finn.framework.datasource.annotations.Pages;
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

    @Pages
    List<AttachmentEntity> getList(AttachmentQuery query);

    boolean updateById(AttachmentEntity entity);

    // @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // 回写ID
    int insertAttach(AttachmentEntity entity);
}