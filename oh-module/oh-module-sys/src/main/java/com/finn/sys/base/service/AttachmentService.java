package com.finn.sys.base.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.base.query.AttachmentQuery;
import com.finn.sys.base.vo.AttachmentVO;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface AttachmentService {

    PageResult<AttachmentVO> page(AttachmentQuery query);

    void save(AttachmentVO vo);

    void update(AttachmentVO vo);

    void delete(List<Long> idList);
}