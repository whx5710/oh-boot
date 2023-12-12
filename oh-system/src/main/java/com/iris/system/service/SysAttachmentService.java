package com.iris.system.service;

import com.iris.system.entity.SysAttachmentEntity;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.system.query.SysAttachmentQuery;
import com.iris.system.vo.SysAttachmentVO;

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