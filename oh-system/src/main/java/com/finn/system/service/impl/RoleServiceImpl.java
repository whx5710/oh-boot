package com.finn.system.service.impl;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.common.constant.CommConstant;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.exception.ServerException;
import com.finn.framework.entity.PageResult;
import com.finn.framework.service.impl.BaseServiceImpl;
import com.finn.framework.utils.AssertUtils;
import com.finn.system.convert.RoleConvert;
import com.finn.system.entity.RoleEntity;
import com.finn.system.enums.DataScopeEnum;
import com.finn.system.mapper.RoleMapper;
import com.finn.system.query.RoleQuery;
import com.finn.system.service.RoleDataScopeService;
import com.finn.system.service.RoleMenuService;
import com.finn.system.service.RoleService;
import com.finn.system.service.UserRoleService;
import com.finn.system.vo.RoleDataScopeVO;
import com.finn.system.vo.RoleVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 角色
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleEntity> implements RoleService {
	private final RoleMenuService roleMenuService;
	private final RoleDataScopeService roleDataScopeService;
	private final UserRoleService userRoleService;
	private final RoleMapper roleMapper;
	private final RedisCache redisCache;

	public RoleServiceImpl(RoleMenuService roleMenuService, RoleDataScopeService roleDataScopeService,
						   UserRoleService userRoleService, RoleMapper roleMapper,
						   RedisCache redisCache) {
		this.roleMenuService = roleMenuService;
		this.roleDataScopeService = roleDataScopeService;
		this.userRoleService = userRoleService;
		this.roleMapper = roleMapper;
		this.redisCache = redisCache;
	}

	@Override
	public PageResult<RoleVO> page(RoleQuery query) {
		// 数据权限
//		query.setSqlFilter(getDataScopeFilter(null,null));
		Page<RoleEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<RoleEntity> list = roleMapper.getList(query);
		List<RoleVO> roles = new ArrayList<>();
		if(list != null && !list.isEmpty()){
			for(RoleEntity item: list){
				RoleVO vo = new RoleVO();
				vo.setId(item.getId());
                vo.setCode(item.getCode());
				vo.setName(item.getName());
				vo.setRemark(item.getRemark());
				vo.setDataScope(item.getDataScope());
				vo.setIsSystem(item.getIsSystem());
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
        return RoleConvert.INSTANCE.convertList(entityList);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(RoleVO vo) {
		// 判断角色编码是否重复
		AssertUtils.isBlank(vo.getCode(), "角色编码");
		List<RoleEntity> list = roleMapper.listByWrapper(QueryWrapper.of(RoleEntity.class).eq(RoleEntity::getDbStatus, 1)
				.eq(RoleEntity::getCode, vo.getCode()));
		if(!list.isEmpty()){
			throw new ServerException("角色编码已存在！", "角色：" + list.getFirst().getName());
		}

		RoleEntity entity = RoleConvert.INSTANCE.convert(vo);
		if(entity.getIsSystem() == null){
			entity.setIsSystem(0);
		}
		// 保存角色
		entity.setDataScope(DataScopeEnum.SELF.getValue());
		roleMapper.insert(entity);

		// 保存角色菜单关系
		roleMenuService.saveOrUpdate(entity.getId(), vo.getMenuIdList());

		// 缓存角色
		entity = roleMapper.findById(entity.getId(), RoleEntity.class);
		cacheRole(entity);
	}

	@Override
	public void update(RoleVO vo) {
		AssertUtils.isNull(vo.getId(), "角色ID");
		RoleEntity entity = RoleConvert.INSTANCE.convert(vo);
		// 更新角色
		roleMapper.updateById(entity);

		// 更新角色菜单关系
		roleMenuService.saveOrUpdate(entity.getId(), vo.getMenuIdList());
		// 缓存角色
		entity = roleMapper.findById(entity.getId(), RoleEntity.class);
		cacheRole(entity);
	}

	@Override
	public void dataScope(RoleDataScopeVO vo) {
		RoleEntity entity = roleMapper.findById(vo.getId(), RoleEntity.class);
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
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		// 删除角色
		// removeByIds(idList);
		idList.forEach(id -> {
            RoleEntity role = roleMapper.findById(id, RoleEntity.class);
            if(role != null && role.getIsSystem() == 1){
                throw new ServerException("系统角色禁止删除");
            }
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
		return roleMapper.findById(id, RoleEntity.class);
	}

	/**
	 * 缓存角色
	 * @param entity role
	 */
	@Override
	public void cacheRole(RoleEntity entity){
		RoleVO vo = RoleConvert.INSTANCE.convert(entity);
		redisCache.set(CommConstant.ROLE__PREFIX + vo.getCode(), vo.toJson());
	}

}