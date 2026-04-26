package com.finn.system.service.impl;

import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.utils.AssertUtils;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.system.entity.RoleMenuEntity;
import com.finn.system.mapper.RoleMenuMapper;
import com.finn.system.service.RoleMenuService;
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
        AssertUtils.isNull(user, "用户信息");
		// 需要新增的菜单ID
		// Collection<Long> insertMenuIdList = CollUtil.subtract(menuIdList, dbMenuIdList);
		Collection<Long> insertMenuIdList = menuIdList.stream()
				.filter(element -> !dbMenuIdList.contains(element))
				.toList();

		if (!insertMenuIdList.isEmpty()){
			List<RoleMenuEntity> menuList = insertMenuIdList.stream().map(menuId -> {
				RoleMenuEntity entity = new RoleMenuEntity();
				entity.setMenuId(menuId);
				entity.setRoleId(roleId);
				entity.setCreator(user.getId());
				entity.setCreateTime(LocalDateTime.now());
				return entity;
			}).collect(Collectors.toList());

			// 批量新增
			long l = roleMenuMapper.insertBatch(menuList);
			System.out.println("总计插入：" + l);
		}

		// 需要删除的菜单ID
		// Collection<Long> deleteMenuIdList = CollUtil.subtract(dbMenuIdList, menuIdList);
		List<Long> deleteMenuIdList = dbMenuIdList.stream()
				.filter(element -> !menuIdList.contains(element))
				.collect(Collectors.toList());

		if (!deleteMenuIdList.isEmpty()){
			UpdateWrapper<RoleMenuEntity> updateWrapper = UpdateWrapper.of(RoleMenuEntity.class)
					.set(RoleMenuEntity::getDbStatus, 0)
					.in(RoleMenuEntity::getMenuId, deleteMenuIdList)
					.eq(RoleMenuEntity::getRoleId, roleId);
			roleMenuMapper.updateByWrapper(updateWrapper);
		}
	}

	@Override
	public List<Long> getMenuIdList(Long roleId){
		return roleMenuMapper.getMenuIdList(roleId);
	}

	@Override
	public void deleteByRoleIdList(List<Long> roleIdList) {
		UpdateWrapper<RoleMenuEntity> updateWrapper = UpdateWrapper.of(RoleMenuEntity.class)
						.set(RoleMenuEntity::getDbStatus, 0)
								.in(RoleMenuEntity::getRoleId, roleIdList);
		roleMenuMapper.updateByWrapper(updateWrapper);
	}

	@Override
	public void deleteByMenuId(Long menuId) {
		UpdateWrapper<RoleMenuEntity> updateWrapper = UpdateWrapper.of(RoleMenuEntity.class)
				.set(RoleMenuEntity::getDbStatus, 0)
						.eq(RoleMenuEntity::getMenuId, menuId);
		roleMenuMapper.updateByWrapper(updateWrapper);
	}

}