package com.finn.support.service;

import com.finn.core.utils.PageResult;
import com.finn.support.entity.RoleEntity;
import com.finn.support.query.RoleQuery;
import com.finn.support.vo.RoleDataScopeVO;
import com.finn.support.vo.RoleVO;

import java.util.List;

/**
 * 角色
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface RoleService {

	PageResult<RoleVO> page(RoleQuery query);

	List<RoleVO> getList(RoleQuery query);

	void save(RoleVO vo);

	void update(RoleVO vo);

	void dataScope(RoleDataScopeVO vo);

	void delete(List<Long> idList);

	RoleEntity getById(Long id);
}
