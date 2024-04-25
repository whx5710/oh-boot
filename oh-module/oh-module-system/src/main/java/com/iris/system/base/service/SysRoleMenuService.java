package com.iris.system.base.service;


import com.iris.framework.datasource.service.BaseService;
import com.iris.system.base.entity.SysRoleMenuEntity;

import java.util.List;


/**
 * 角色与菜单对应关系
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenuEntity> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> getMenuIdList(Long roleId);

	/**
	 * 保存或修改
	 * @param roleId      角色ID
	 * @param menuIdList  菜单ID列表
	 */
	void saveOrUpdate(Long roleId, List<Long> menuIdList);

	/**
	 * 根据角色id列表，删除角色菜单关系
	 * @param roleIdList 角色id列表
	 */
	void deleteByRoleIdList(List<Long> roleIdList);

	/**
	 * 根据菜单id，删除角色菜单关系
	 * @param menuId 菜单id
	 */
	void deleteByMenuId(Long menuId);
}