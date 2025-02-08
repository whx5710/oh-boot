package com.finn.support.service;

import com.finn.core.utils.PageResult;
import com.finn.support.entity.SysRoleEntity;
import com.finn.support.query.SysRoleQuery;
import com.finn.support.vo.SysRoleDataScopeVO;
import com.finn.support.vo.SysRoleVO;

import java.util.List;

/**
 * 角色
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysRoleService {

	PageResult<SysRoleVO> page(SysRoleQuery query);

	List<SysRoleVO> getList(SysRoleQuery query);

	void save(SysRoleVO vo);

	void update(SysRoleVO vo);

	void dataScope(SysRoleDataScopeVO vo);

	void delete(List<Long> idList);

	SysRoleEntity getById(Long id);
}
