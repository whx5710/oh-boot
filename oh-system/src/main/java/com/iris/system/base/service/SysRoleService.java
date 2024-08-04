package com.iris.system.base.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.system.base.entity.SysRoleEntity;
import com.iris.system.base.query.SysRoleQuery;
import com.iris.system.base.vo.SysRoleDataScopeVO;
import com.iris.system.base.vo.SysRoleVO;

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
