package com.iris.system.base.dao;

import com.iris.system.base.entity.SysAttachmentEntity;
import com.iris.system.base.query.SysAttachmentQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface SysAttachmentDao {

    List<SysAttachmentEntity> getList(SysAttachmentQuery query);

    boolean updateById(SysAttachmentEntity entity);

    int insertAttach(SysAttachmentEntity entity);
}