package com.finn.sys.base.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.sys.base.entity.MenuEntity;
import com.finn.sys.base.query.MenuQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuEntity> {

	List<MenuEntity> getList(MenuEntity query);
	/**
	 * 查询所有菜单列表
	 *
	 * @param query params
	 */
	List<MenuEntity> getMenuList(@Param("query") MenuQuery query);

	/**
	 * 查询用户菜单列表
	 *
	 * @param userId 用户ID
	 * @param query 菜单参数
	 */
	List<MenuEntity> getUserMenuList(@Param("userId") Long userId, @Param("query") MenuQuery query);

	// int save(MenuEntity param);

	// boolean updateById(MenuEntity param);

	MenuEntity getById(@Param("id")Long id);

}
