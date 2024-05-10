package com.iris.system.base.service;

import com.iris.system.base.vo.SysMenuVO;
import com.iris.system.base.entity.SysMenuEntity;
import com.iris.framework.datasource.service.BaseService;
import com.iris.framework.security.user.UserDetail;

import java.util.List;
import java.util.Set;


/**
 * 菜单管理
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysMenuService extends BaseService<SysMenuEntity> {

	void save(SysMenuVO vo);

	void update(SysMenuVO vo);

	void delete(Long id);

	/**
	 * 菜单列表
	 *
	 * @param type 菜单类型
	 */
	List<SysMenuVO> getMenuList(Integer type);

	/**
	 * 用户菜单列表
	 *
	 * @param user  用户
	 * @param type 菜单类型
	 */
	List<SysMenuVO> getUserMenuList(UserDetail user, Integer type);

	/**
	 * 获取子菜单的数量
	 * @param pid  父菜单ID
	 */
	Long getSubMenuCount(Long pid);

	/**
	 * 获取用户权限列表
	 */
	Set<String> getUserAuthority(UserDetail user);
}
