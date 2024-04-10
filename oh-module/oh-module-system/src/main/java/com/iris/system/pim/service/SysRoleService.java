package com.iris.system.pim.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;
import com.iris.system.pim.entity.SysRoleEntity;
import com.iris.system.pim.query.SysRoleQuery;
import com.iris.system.pim.vo.SysRoleDataScopeVO;
import com.iris.system.pim.vo.SysRoleVO;

import java.util.List;

/**
 * 角色
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysRoleService extends BaseService<SysRoleEntity> {

	PageResult<SysRoleVO> page(SysRoleQuery query);

	List<SysRoleVO> getList(SysRoleQuery query);

	void save(SysRoleVO vo);

	void update(SysRoleVO vo);

	void dataScope(SysRoleDataScopeVO vo);

	void delete(List<Long> idList);
}
