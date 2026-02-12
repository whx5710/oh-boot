package com.finn.system.service;

import com.finn.core.utils.PageResult;
import com.finn.system.entity.PostEntity;
import com.finn.system.query.PostQuery;
import com.finn.system.vo.PostVO;

import java.util.List;

/**
 * 岗位管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface PostService {

    PageResult<PostVO> page(PostQuery query);

    List<PostVO> getList();

    void save(PostVO vo);

    void update(PostVO vo);

    void delete(List<Long> idList);

    PostEntity getById(Long id);
}