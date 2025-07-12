package com.finn.support.service.impl;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.framework.service.impl.BaseServiceImpl;
import com.finn.support.cache.TenantCache;
import com.finn.support.mapper.RoleMapper;
import com.finn.support.enums.DataScopeEnum;
import com.finn.support.query.RoleQuery;
import com.finn.support.vo.RoleDataScopeVO;
import com.finn.support.vo.RoleVO;
import com.finn.support.convert.RoleConvert;
import com.finn.support.service.RoleMenuService;
import com.finn.support.service.RoleService;
import com.finn.support.service.RoleDataScopeService;
import com.finn.support.service.UserRoleService;
import com.finn.core.utils.PageResult;
import com.finn.support.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 角色
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
@Ds(Constant.DYNAMIC_SYS_DB)
public class RoleServiceImpl extends BaseServiceImpl<RoleEntity> implements RoleService {
	private final RoleMenuService roleMenuService;
	private final RoleDataScopeService roleDataScopeService;
	private final UserRoleService userRoleService;
	private final RoleMapper roleMapper;
	private final TenantCache tenantCache;

	public RoleServiceImpl(RoleMenuService roleMenuService, RoleDataScopeService roleDataScopeService,
						   UserRoleService userRoleService, RoleMapper roleMapper, TenantCache tenantCache) {
		this.roleMenuService = roleMenuService;
		this.roleDataScopeService = roleDataScopeService;
		this.userRoleService = userRoleService;
		this.roleMapper = roleMapper;
		this.tenantCache = tenantCache;
	}

	@Override
	public PageResult<RoleVO> page(RoleQuery query) {
		// 数据权限
		query.setSqlFilter(getDataScopeFilter(null,null));
		Page<RoleEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<RoleEntity> list = roleMapper.getList(query);
		List<RoleVO> roles = new ArrayList<>();
		if(list != null && !list.isEmpty()){
			for(RoleEntity item: list){
				RoleVO vo = new RoleVO();
				vo.setId(item.getId());
				vo.setName(item.getName());
				vo.setRemark(item.getRemark());
				vo.setDataScope(item.getDataScope());
				vo.setIsSystem(item.getIsSystem());
				vo.setTenantId(item.getTenantId());
				vo.setTenantName(item.getTenantName());
				vo.setCreateTime(item.getCreateTime());
				if(item.getMenuIds() != null){
					String[] m = item.getMenuIds().split(",");
					List<Long> longList = new ArrayList<>();
					for(String s: m){
						longList.add(Long.valueOf(s));
					}
					vo.setMenuIdList(longList);
				}
				roles.add(vo);
			}
		}
		return new PageResult<>(roles, page.getTotal());
	}

	@Override
	public List<RoleVO> getList(RoleQuery query) {
		List<RoleEntity> entityList = roleMapper.getList(query);
		List<RoleVO> voList = RoleConvert.INSTANCE.convertList(entityList);
		for(RoleVO vo: voList){
			vo.setTenantName(tenantCache.getNameByTenantId(vo.getTenantId()));
		}
		return voList;
	}

	@Override
	public void save(RoleVO vo) {
		RoleEntity entity = RoleConvert.INSTANCE.convert(vo);
		if(entity.getIsSystem() == null){
			entity.setIsSystem(0);
		}
		if(entity.getIsSystem() == 1){
			entity.setTenantId(null);
		}
		// 保存角色
		entity.setDataScope(DataScopeEnum.SELF.getValue());
		roleMapper.insertRole(entity);

		// 保存角色菜单关系
		roleMenuService.saveOrUpdate(entity.getId(), vo.getMenuIdList());
	}

	@Override
	public void update(RoleVO vo) {
		RoleEntity entity = RoleConvert.INSTANCE.convert(vo);
		// 更新角色
		roleMapper.updateById(entity);

		// 更新角色菜单关系
		roleMenuService.saveOrUpdate(entity.getId(), vo.getMenuIdList());
	}

	@Override
	public void dataScope(RoleDataScopeVO vo) {
		RoleEntity entity = roleMapper.getById(vo.getId());
		entity.setDataScope(vo.getDataScope());
		// 更新角色
		roleMapper.updateById(entity);

		// 更新角色数据权限关系
		if(vo.getDataScope().equals(DataScopeEnum.CUSTOM.getValue())){
			roleDataScopeService.saveOrUpdate(entity.getId(), vo.getDeptIdList());
		}else {
			roleDataScopeService.deleteByRoleIdList(Collections.singletonList(vo.getId()));
		}
	}

	@Override
	public void delete(List<Long> idList) {
		// 删除角色
		// removeByIds(idList);
		idList.forEach(id -> {
			RoleEntity sysRole = new RoleEntity();
			sysRole.setId(id);
			sysRole.setDbStatus(0);
			roleMapper.updateById(sysRole);
		});

		// 删除用户角色关系
		userRoleService.deleteByRoleIdList(idList);

		// 删除角色菜单关系
		roleMenuService.deleteByRoleIdList(idList);

		// 删除角色数据权限关系
		roleDataScopeService.deleteByRoleIdList(idList);
	}

	@Override
	public RoleEntity getById(Long id) {
		return roleMapper.getById(id);
	}

}