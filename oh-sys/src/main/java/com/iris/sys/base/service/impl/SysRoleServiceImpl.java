package com.iris.sys.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.datasource.service.impl.BaseServiceImpl;
import com.iris.sys.base.mapper.SysRoleMapper;
import com.iris.sys.base.enums.DataScopeEnum;
import com.iris.sys.base.query.SysRoleQuery;
import com.iris.sys.base.vo.SysRoleDataScopeVO;
import com.iris.sys.base.vo.SysRoleVO;
import com.iris.sys.base.convert.SysRoleConvert;
import com.iris.sys.base.service.SysRoleMenuService;
import com.iris.sys.base.service.SysRoleService;
import com.iris.sys.base.service.SysRoleDataScopeService;
import com.iris.sys.base.service.SysUserRoleService;
import com.iris.core.utils.PageResult;
import com.iris.sys.base.entity.SysRoleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 角色
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl implements SysRoleService {
	private final SysRoleMenuService sysRoleMenuService;
	private final SysRoleDataScopeService sysRoleDataScopeService;
	private final SysUserRoleService sysUserRoleService;
	private final SysRoleMapper sysRoleMapper;

	public SysRoleServiceImpl(SysRoleMenuService sysRoleMenuService, SysRoleDataScopeService sysRoleDataScopeService,
							  SysUserRoleService sysUserRoleService, SysRoleMapper sysRoleMapper) {
		this.sysRoleMenuService = sysRoleMenuService;
		this.sysRoleDataScopeService = sysRoleDataScopeService;
		this.sysUserRoleService = sysUserRoleService;
		this.sysRoleMapper = sysRoleMapper;
	}

	@Override
	public PageResult<SysRoleVO> page(SysRoleQuery query) {
		// 数据权限
		query.setSqlFilter(getDataScopeFilter(null,null));

		PageHelper.startPage(query.getPage(), query.getLimit());
		List<SysRoleEntity> list = sysRoleMapper.getList(query);
		PageInfo<SysRoleEntity> pageInfo = new PageInfo<>(list);
		return new PageResult<>(SysRoleConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
	}

	@Override
	public List<SysRoleVO> getList(SysRoleQuery query) {
		List<SysRoleEntity> entityList = sysRoleMapper.getList(query);
		return SysRoleConvert.INSTANCE.convertList(entityList);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysRoleVO vo) {
		SysRoleEntity entity = SysRoleConvert.INSTANCE.convert(vo);

		// 保存角色
		entity.setDataScope(DataScopeEnum.SELF.getValue());
		sysRoleMapper.insertRole(entity);

		// 保存角色菜单关系
		sysRoleMenuService.saveOrUpdate(entity.getId(), vo.getMenuIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysRoleVO vo) {
		SysRoleEntity entity = SysRoleConvert.INSTANCE.convert(vo);

		// 更新角色
		sysRoleMapper.updateById(entity);

		// 更新角色菜单关系
		sysRoleMenuService.saveOrUpdate(entity.getId(), vo.getMenuIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void dataScope(SysRoleDataScopeVO vo) {
		SysRoleEntity entity = sysRoleMapper.getById(vo.getId());
		entity.setDataScope(vo.getDataScope());
		// 更新角色
		sysRoleMapper.updateById(entity);

		// 更新角色数据权限关系
		if(vo.getDataScope().equals(DataScopeEnum.CUSTOM.getValue())){
			sysRoleDataScopeService.saveOrUpdate(entity.getId(), vo.getOrgIdList());
		}else {
			sysRoleDataScopeService.deleteByRoleIdList(Collections.singletonList(vo.getId()));
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		// 删除角色
		// removeByIds(idList);
		idList.forEach(id -> {
			SysRoleEntity sysRole = new SysRoleEntity();
			sysRole.setId(id);
			sysRole.setDbStatus(0);
			sysRoleMapper.updateById(sysRole);
		});

		// 删除用户角色关系
		sysUserRoleService.deleteByRoleIdList(idList);

		// 删除角色菜单关系
		sysRoleMenuService.deleteByRoleIdList(idList);

		// 删除角色数据权限关系
		sysRoleDataScopeService.deleteByRoleIdList(idList);
	}

	@Override
	public SysRoleEntity getById(Long id) {
		return sysRoleMapper.getById(id);
	}

}