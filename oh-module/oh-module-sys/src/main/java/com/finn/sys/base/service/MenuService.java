package com.finn.sys.base.service;

import com.finn.core.utils.PageResult;
import com.finn.framework.security.user.UserDetail;
import com.finn.sys.base.entity.MenuEntity;
import com.finn.sys.base.query.MenuQuery;
import com.finn.sys.base.vo.MenuTreeVO;
import com.finn.sys.base.vo.MenuVO;

import java.util.List;
import java.util.Set;


/**
 * 菜单管理
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface MenuService {

	void save(MenuTreeVO vo);

	void update(MenuTreeVO vo);

	void delete(Long id);

	/**
	 * 菜单列表
	 *
	 * @param query 菜单
	 */
	PageResult<MenuVO> page(MenuQuery query);


	/**
	 * 菜单列表
	 *
	 * @param query 菜单类型
	 */
	List<MenuTreeVO> getMenuTreeList(MenuQuery query);

	/**
	 * 用户菜单列表
	 *
	 * @param user  用户
	 * @param type 菜单类型
	 */
	List<MenuTreeVO> getUserMenuList(UserDetail user, Integer type);

	/**
	 * 获取子菜单的数量
	 * @param pid  父菜单ID
	 */
	Long getSubMenuCount(Long pid);

	/**
	 * 获取用户权限列表
	 */
	Set<String> getUserAuthority(UserDetail user);

	MenuEntity getById(Long id);
}
