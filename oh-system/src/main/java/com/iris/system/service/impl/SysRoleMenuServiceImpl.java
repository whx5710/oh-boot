package com.iris.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iris.system.service.SysRoleMenuService;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.system.dao.SysRoleMenuDao;
import com.iris.system.entity.SysRoleMenuEntity;
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
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		// 数据库菜单ID列表
		List<Long> dbMenuIdList = getMenuIdList(roleId);

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
			saveBatch(menuList);
		}

		// 需要删除的菜单ID
		Collection<Long> deleteMenuIdList = CollUtil.subtract(dbMenuIdList, menuIdList);
		if (CollUtil.isNotEmpty(deleteMenuIdList)){
			LambdaQueryWrapper<SysRoleMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
			remove(queryWrapper.eq(SysRoleMenuEntity::getRoleId, roleId).in(SysRoleMenuEntity::getMenuId, deleteMenuIdList));
		}
	}

	@Override
	public List<Long> getMenuIdList(Long roleId){
		return baseMapper.getMenuIdList(roleId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByRoleIdList(List<Long> roleIdList) {
		remove(new LambdaQueryWrapper<SysRoleMenuEntity>().in(SysRoleMenuEntity::getRoleId, roleIdList));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByMenuId(Long menuId) {
		remove(new LambdaQueryWrapper<SysRoleMenuEntity>().eq(SysRoleMenuEntity::getMenuId, menuId));
	}

}