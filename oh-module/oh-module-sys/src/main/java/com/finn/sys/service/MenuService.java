package com.finn.sys.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.entity.MenuEntity;
import com.finn.sys.query.MenuQuery;
import com.finn.sys.vo.MenuTreeVO;
import com.finn.sys.vo.MenuVO;
import com.finn.sys.vo.RouteVO;

import java.util.List;


/**
 * 菜单管理
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface MenuService {

	void save(MenuVO vo);

	void update(MenuVO vo);

	void delete(Long id);

	/**
	 * 菜单列表
	 *
	 * @param query 菜单
	 */
	PageResult<MenuVO> page(MenuQuery query);

	/**
	 * 菜单列表
	 * @param query
	 * @return
	 */
	List<MenuVO> list(MenuQuery query);

	/**
	 * 菜单列表
	 *
	 * @param query 菜单类型
	 */
	List<MenuTreeVO> getMenuTreeList(MenuQuery query);

	/**
	 * 用户菜单列表
	 *
	 * @param query 菜单类型
	 */
	List<MenuTreeVO> getUserMenuList(MenuQuery query);

	/**
	 * 用户菜单列表-route
	 *
	 * @param query 菜单参数
	 */
	List<RouteVO> getUserRouteList(MenuQuery query);

	/**
	 * 获取子菜单的数量
	 * @param pid  父菜单ID
	 */
	Long getSubMenuCount(Long pid);

	MenuEntity getById(Long id);

	/**
	 * 名称是否存在
	 * @param  id 不包含ID对应的数据
	 * @param name 名称
	 * @return b
	 */
	Boolean nameExists(Long id, String name);

	/**
	 * 路径是否存在
	 * @param  id 不包含ID对应的数据
	 * @param path 路径
	 * @return b
	 */
	Boolean pathExists(Long id, String path);
}
