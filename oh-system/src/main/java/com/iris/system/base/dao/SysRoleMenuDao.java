package com.iris.system.base.dao;

import com.iris.system.base.entity.SysRoleMenuEntity;
import com.iris.framework.datasource.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> getMenuIdList(@Param("roleId") Long roleId);

	int saveBatch(@Param("list") List<SysRoleMenuEntity> menuList);

	int deleteByRoleIdList(@Param("list") List<Long> roleIdList, @Param("param")SysRoleMenuEntity param);

	int deleteByMenuId(@Param("menuId")Long menuId, @Param("param")SysRoleMenuEntity param);

	int deleteMenuIdList(@Param("list") List<Long> menuIdList, @Param("param")SysRoleMenuEntity param);
}
