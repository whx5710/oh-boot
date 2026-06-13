package com.finn.system.mapper;

import com.finn.framework.aop.annotations.Pages;
import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.system.entity.AttachmentEntity;
import com.finn.system.query.AttachmentQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface AttachmentMapper extends BaseMapper<AttachmentEntity> {

    @Pages
    List<AttachmentEntity> getList(AttachmentQuery query);
}