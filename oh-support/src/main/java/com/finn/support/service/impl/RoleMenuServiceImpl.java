package com.finn.support.service.impl;

import cn.hutool.core.collection.CollUtil;
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
		Collection<Long> insertMenuIdList = CollUtil.subtract(menuIdList, dbMenuIdList);
		if (CollUtil.isNotEmpty(insertMenuIdList)){
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
		Collection<Long> deleteMenuIdList = CollUtil.subtract(dbMenuIdList, menuIdList);
		if (CollUtil.isNotEmpty(deleteMenuIdList)){
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