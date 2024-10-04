package com.iris.sys.base.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.sys.base.entity.SysMenuEntity;
import com.iris.sys.base.query.SysMenuQuery;
import com.iris.sys.base.vo.SysMenuTreeVO;
import com.iris.framework.security.user.UserDetail;
import com.iris.sys.base.vo.SysMenuVO;

import java.util.List;
import java.util.Set;


/**
 * 菜单管理
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysMenuService {

	void save(SysMenuTreeVO vo);

	void update(SysMenuTreeVO vo);

	void delete(Long id);

	/**
	 * 菜单列表
	 *
	 * @param query 菜单
	 */
	PageResult<SysMenuVO> page(SysMenuQuery query);


	/**
	 * 菜单列表
	 *
	 * @param query 菜单类型
	 */
	List<SysMenuTreeVO> getMenuTreeList(SysMenuQuery query);

	/**
	 * 用户菜单列表
	 *
	 * @param user  用户
	 * @param type 菜单类型
	 */
	List<SysMenuTreeVO> getUserMenuList(UserDetail user, Integer type);

	/**
	 * 获取子菜单的数量
	 * @param pid  父菜单ID
	 */
	Long getSubMenuCount(Long pid);

	/**
	 * 获取用户权限列表
	 */
	Set<String> getUserAuthority(UserDetail user);

	SysMenuEntity getById(Long id);
}
