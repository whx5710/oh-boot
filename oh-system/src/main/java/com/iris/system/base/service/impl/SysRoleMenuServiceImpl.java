package com.iris.system.base.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.iris.system.base.dao.SysRoleMenuDao;
import com.iris.system.base.service.SysRoleMenuService;
import com.iris.system.base.entity.SysRoleMenuEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

	private final SysRoleMenuDao sysRoleMenuDao;

	public SysRoleMenuServiceImpl(SysRoleMenuDao sysRoleMenuDao){
		this.sysRoleMenuDao = sysRoleMenuDao;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		// 数据库菜单ID列表
		List<Long> dbMenuIdList = sysRoleMenuDao.getMenuIdList(roleId);

		// 需要新增的菜单ID
		Collection<Long> insertMenuIdList = CollUtil.subtract(menuIdList, dbMenuIdList);
		if (CollUtil.isNotEmpty(insertMenuIdList)){
			List<SysRoleMenuEntity> menuList = insertMenuIdList.stream().map(menuId -> {
				SysRoleMenuEntity entity = new SysRoleMenuEntity();
				entity.setMenuId(menuId);
				entity.setRoleId(roleId);
				return entity;
			}).collect(Collectors.toList());

			// 批量新增
			sysRoleMenuDao.saveBatch(menuList);
		}

		// 需要删除的菜单ID
		Collection<Long> deleteMenuIdList = CollUtil.subtract(dbMenuIdList, menuIdList);
		if (CollUtil.isNotEmpty(deleteMenuIdList)){
			SysRoleMenuEntity param = new SysRoleMenuEntity();
			param.setRoleId(roleId);
			sysRoleMenuDao.deleteMenuIdList((List<Long>) deleteMenuIdList, param);
		}
	}

	@Override
	public List<Long> getMenuIdList(Long roleId){
		return sysRoleMenuDao.getMenuIdList(roleId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByRoleIdList(List<Long> roleIdList) {
		sysRoleMenuDao.deleteByRoleIdList(roleIdList, new SysRoleMenuEntity());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByMenuId(Long menuId) {
		sysRoleMenuDao.deleteByMenuId(menuId, new SysRoleMenuEntity());
	}

}