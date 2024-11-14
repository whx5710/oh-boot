package com.iris.sys.base.service;

import com.iris.sys.base.query.SysAttachmentQuery;
import com.iris.sys.base.vo.SysAttachmentVO;
import com.iris.core.utils.PageResult;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysAttachmentService {

    PageResult<SysAttachmentVO> page(SysAttachmentQuery query);

    void save(SysAttachmentVO vo);

    void update(SysAttachmentVO vo);

    void delete(List<Long> idList);
}