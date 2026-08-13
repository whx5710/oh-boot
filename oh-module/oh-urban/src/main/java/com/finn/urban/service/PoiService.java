package com.finn.urban.service;

import com.finn.common.entity.PageResult;
import com.finn.urban.query.PoiQuery;
import com.finn.urban.vo.PoiVO;

import java.util.List;

/**
 * 兴趣点
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-13 15:09:28
 *
 */
public interface PoiService {

    PageResult<PoiVO> page(PoiQuery query);

    Long save(PoiVO vo);

    void update(PoiVO vo);

    void delete(List<Long> idList);
}
