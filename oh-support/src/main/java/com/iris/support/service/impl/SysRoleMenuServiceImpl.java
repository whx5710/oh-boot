package com.iris.support.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.iris.support.mapper.SysRoleMenuMapper;
import com.iris.support.service.SysRoleMenuService;
import com.iris.support.entity.SysRoleMenuEntity;
import org.springframework.stereotype.Service;

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

	private final SysRoleMenuMapper sysRoleMenuMapper;

	public SysRoleMenuServiceImpl(SysRoleMenuMapper sysRoleMenuMapper){
		this.sysRoleMenuMapper = sysRoleMenuMapper;
	}

	@Override
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		// 数据库菜单ID列表
		List<Long> dbMenuIdList = sysRoleMenuMapper.getMenuIdList(roleId);

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
			sysRoleMenuMapper.saveBatch(menuList);
		}

		// 需要删除的菜单ID
		Collection<Long> deleteMenuIdList = CollUtil.subtract(dbMenuIdList, menuIdList);
		if (CollUtil.isNotEmpty(deleteMenuIdList)){
			SysRoleMenuEntity param = new SysRoleMenuEntity();
			param.setRoleId(roleId);
			sysRoleMenuMapper.deleteMenuIdList((List<Long>) deleteMenuIdList, param);
		}
	}

	@Override
	public List<Long> getMenuIdList(Long roleId){
		return sysRoleMenuMapper.getMenuIdList(roleId);
	}

	@Override
	public void deleteByRoleIdList(List<Long> roleIdList) {
		sysRoleMenuMapper.deleteByRoleIdList(roleIdList, new SysRoleMenuEntity());
	}

	@Override
	public void deleteByMenuId(Long menuId) {
		sysRoleMenuMapper.deleteByMenuId(menuId, new SysRoleMenuEntity());
	}

}