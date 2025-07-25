package com.finn.support.service.impl;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.mapper.RoleMenuMapper;
import com.finn.support.service.RoleMenuService;
import com.finn.support.entity.RoleMenuEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 角色与菜单对应关系
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
@Ds(Constant.DYNAMIC_SYS_DB)
public class RoleMenuServiceImpl implements RoleMenuService {

	private final RoleMenuMapper roleMenuMapper;

	public RoleMenuServiceImpl(RoleMenuMapper roleMenuMapper){
		this.roleMenuMapper = roleMenuMapper;
	}

	@Override
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		// 数据库菜单ID列表
		List<Long> dbMenuIdList = roleMenuMapper.getMenuIdList(roleId);
		UserDetail user = SecurityUser.getUser();
		// 需要新增的菜单ID
		// Collection<Long> insertMenuIdList = CollUtil.subtract(menuIdList, dbMenuIdList);
		Collection<Long> insertMenuIdList = menuIdList.stream()
				.filter(element -> !dbMenuIdList.contains(element))
				.collect(Collectors.toList());

		if (insertMenuIdList != null && insertMenuIdList.size() > 0){
			List<RoleMenuEntity> menuList = insertMenuIdList.stream().map(menuId -> {
				RoleMenuEntity entity = new RoleMenuEntity();
				entity.setMenuId(menuId);
				entity.setRoleId(roleId);
				entity.setCreator(user.getId());
				entity.setCreateTime(LocalDateTime.now());
				return entity;
			}).collect(Collectors.toList());

			// 批量新增
			roleMenuMapper.saveBatch(menuList);
		}

		// 需要删除的菜单ID
		// Collection<Long> deleteMenuIdList = CollUtil.subtract(dbMenuIdList, menuIdList);
		Collection<Long> deleteMenuIdList = dbMenuIdList.stream()
				.filter(element -> !menuIdList.contains(element))
				.collect(Collectors.toList());

		if (deleteMenuIdList != null && deleteMenuIdList.size() > 0){
			RoleMenuEntity param = new RoleMenuEntity();
			param.setRoleId(roleId);
			param.setUpdater(user.getId());
			param.setUpdateTime(LocalDateTime.now());
			roleMenuMapper.deleteMenuIdList((List<Long>) deleteMenuIdList, param);
		}
	}

	@Override
	public List<Long> getMenuIdList(Long roleId){
		return roleMenuMapper.getMenuIdList(roleId);
	}

	@Override
	public void deleteByRoleIdList(List<Long> roleIdList) {
		roleMenuMapper.deleteByRoleIdList(roleIdList, new RoleMenuEntity());
	}

	@Override
	public void deleteByMenuId(Long menuId) {
		roleMenuMapper.deleteByMenuId(menuId, new RoleMenuEntity());
	}

}