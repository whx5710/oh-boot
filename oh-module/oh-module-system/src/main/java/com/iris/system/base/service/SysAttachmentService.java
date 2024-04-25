package com.iris.system.base.service;

import com.iris.system.base.query.SysAttachmentQuery;
import com.iris.system.base.vo.SysAttachmentVO;
import com.iris.system.base.entity.SysAttachmentEntity;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysAttachmentService extends BaseService<SysAttachmentEntity> {

    PageResult<SysAttachmentVO> page(SysAttachmentQuery query);

    void save(SysAttachmentVO vo);

    void update(SysAttachmentVO vo);

    void delete(List<Long> idList);
}