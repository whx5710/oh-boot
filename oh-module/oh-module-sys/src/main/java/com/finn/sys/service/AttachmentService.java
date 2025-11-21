package com.finn.sys.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.query.AttachmentQuery;
import com.finn.sys.vo.AttachmentVO;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface AttachmentService {

    PageResult<AttachmentVO> page(AttachmentQuery query);

    Long save(AttachmentVO vo);

    void update(AttachmentVO vo);

    void delete(List<Long> idList);
}