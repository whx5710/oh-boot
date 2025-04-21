package com.finn.support.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.JsonUtils;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.TreeUtils;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.cache.TenantCache;
import com.finn.support.mapper.OrgMapper;
import com.finn.support.mapper.UserMapper;
import com.finn.support.query.OrgQuery;
import com.finn.support.vo.OrgVO;
import com.finn.support.convert.OrgConvert;
import com.finn.core.exception.ServerException;
import com.finn.support.entity.OrgEntity;
import com.finn.support.service.OrgService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class OrgServiceImpl implements OrgService {
	private final UserMapper userMapper;
	private final OrgMapper orgMapper;

	private final RedisCache redisCache;

	private final TenantCache tenantCache;

	public OrgServiceImpl(UserMapper userMapper, OrgMapper orgMapper,
						  RedisCache redisCache, TenantCache tenantCache) {
		this.userMapper = userMapper;
		this.orgMapper = orgMapper;
		this.redisCache = redisCache;
		this.tenantCache = tenantCache;
	}

	@Override
	public List<OrgVO> getList(OrgQuery query) {
		// 数据权限
		// params.put(Constant.DATA_SCOPE, getDataScope("t1", "id"));

		// 机构列表
		List<OrgEntity> entityList = orgMapper.getList(query);

		List<OrgVO> voList = OrgConvert.INSTANCE.convertList(entityList);
		UserDetail user = SecurityUser.getUser();
		if(user != null && user.getSuperAdmin() == 1){
			for(OrgVO item : voList){
				if(item.getTenantId() != null){
					item.setTenantName(tenantCache.getNameByTenantId(item.getTenantId()));
					item.setName(item.getName() +" [" + item.getTenantName() + "]");
				}
			}
		}
		return TreeUtils.build(voList);
	}

	/**
	 * 机构分页列表
	 * @param query 参数
	 * @return e
	 */
	@Override
	public PageResult<OrgVO> page(OrgQuery query) {
		Page<OrgEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
		// 机构列表
		List<OrgEntity> list = orgMapper.getList(query);
		// 如果未获取到机构数据，且用户为租户，且parent_id = 0
		if((list == null || list.size() == 0) && query.getParentId() != null && query.getParentId() == 0L){
			UserDetail user = SecurityUser.getUser();
			if(user != null && user.getSuperAdmin() != 1 && SecurityUser.isTenant()){
				page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
				// 机构列表
				query.setParentId(user.getOrgId());
				list = orgMapper.getList(query);
			}
		}
		List<OrgVO> voList = OrgConvert.INSTANCE.convertList(list);
		for(OrgVO vo: voList){
			vo.setTenantName(tenantCache.getNameByTenantId(vo.getTenantId()));
		}
		return new PageResult<>(voList, page.getTotal());
	}

	@Override
	public void save(OrgVO vo) {
		OrgEntity entity = OrgConvert.INSTANCE.convert(vo);
		// 判断是否租户新增部门，如果是租户，根部门对应该租户的所属部门
		UserDetail user = SecurityUser.getUser();
		if(user != null && entity.getParentId() == 0L){
			if(user.getSuperAdmin() != 1 && user.getTenantId() != null && !user.getTenantId().isEmpty()){
				entity.setParentId(user.getOrgId()==null?entity.getParentId():user.getOrgId());
			}
		}
		orgMapper.insertOrg(entity);
	}

	@Override
	public void update(OrgVO vo) {
		OrgEntity entity = OrgConvert.INSTANCE.convert(vo);

		// 上级机构不能为自身
		if(entity.getId().equals(entity.getParentId())){
			throw new ServerException("上级机构不能为自身");
		}
		// 上级机构不能为下级
		List<Long> subOrgList = getSubOrgIdList(entity.getId());
		if(subOrgList.contains(entity.getParentId())){
			throw new ServerException("上级机构不能为下级");
		}
		// 防止租户新增部门到根节点
		UserDetail user = SecurityUser.getUser();
		if(user != null && entity.getParentId() == 0L) {
			if (user.getSuperAdmin() != 1 && user.getTenantId() != null && !user.getTenantId().isEmpty()) {
				entity.setParentId(user.getOrgId()==null?entity.getParentId():user.getOrgId());
			}
		}
		orgMapper.updateById(entity);
	}

	@Override
	public void delete(Long id) {
		// 判断是否有子机构
		int orgCount = orgMapper.countByParentId(id);
		if(orgCount > 0){
			throw new ServerException("请先删除子机构");
		}

		// 判断机构下面是否有用户
		long userCount = userMapper.countByOrgId(id);
		if(userCount > 0){
			throw new ServerException("机构下面有用户，不能删除");
		}

		// 删除
		// removeById(id);
		OrgEntity params = new OrgEntity();
		params.setId(id);
		params.setDbStatus(0);
		orgMapper.updateById(params);
	}

	@Override
	public List<Long> getSubOrgIdList(Long id) {
		// 所有机构的id、pid列表
		List<OrgEntity> orgList = orgMapper.getIdAndPidList();

		// 递归查询所有子机构ID列表
		List<Long> subIdList = new ArrayList<>();
		getTree(id, orgList, subIdList);

		// 本机构也添加进去
		subIdList.add(id);

		return subIdList;
	}

	@Override
	public OrgEntity getById(Long id) {
		AssertUtils.isNull(id, "机构ID");
		return orgMapper.getById(id);
	}

	/**
	 * 根据ID获取机构信息
	 * @param id 用户ID
	 * @param cache 为true则优先读取缓存
	 * @return 机构信息
	 */
	@Override
	public OrgEntity getById(Long id, Boolean cache) {
		AssertUtils.isNull(id, "机构ID");
		if(cache == null){
			cache = false;
		}
		String key = RedisKeys.getOrgCacheKey(id);
		if(!redisCache.hasKey(key)){
			OrgEntity org = orgMapper.getById(id);
			if(org != null && org.getId() != null){
				redisCache.set(key, org, 7200);// 缓存2小时
			}
			return org;
		}else{
			if(cache){
				return JsonUtils.convertValue(redisCache.get(key), OrgEntity.class);
			}else{
				return orgMapper.getById(id);
			}
		}
	}

	/**
	 * 递归查询ID下面的机构
	 * @param id
	 * @param orgList
	 * @param subIdList
	 */
	private void getTree(Long id, List<OrgEntity> orgList, List<Long> subIdList) {
		for(OrgEntity org : orgList){
			if (org.getParentId().equals(id)){
				getTree(org.getId(), orgList, subIdList);
				subIdList.add(org.getId());
			}
		}
	}
}
