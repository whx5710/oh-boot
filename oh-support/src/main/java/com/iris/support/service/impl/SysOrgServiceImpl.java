package com.iris.support.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.core.cache.RedisCache;
import com.iris.core.cache.RedisKeys;
import com.iris.core.utils.AssertUtils;
import com.iris.core.utils.JsonUtils;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.TreeUtils;
import com.iris.support.mapper.SysOrgMapper;
import com.iris.support.mapper.SysUserMapper;
import com.iris.support.query.SysOrgQuery;
import com.iris.support.vo.SysOrgVO;
import com.iris.support.convert.SysOrgConvert;
import com.iris.core.exception.ServerException;
import com.iris.support.entity.SysOrgEntity;
import com.iris.support.service.SysOrgService;
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
public class SysOrgServiceImpl implements SysOrgService {
	private final SysUserMapper sysUserMapper;
	private final SysOrgMapper sysOrgMapper;

	private final RedisCache redisCache;

	public SysOrgServiceImpl(SysUserMapper sysUserMapper, SysOrgMapper sysOrgMapper,
							 RedisCache redisCache) {
		this.sysUserMapper = sysUserMapper;
		this.sysOrgMapper = sysOrgMapper;
		this.redisCache = redisCache;
	}

	@Override
	public List<SysOrgVO> getList(SysOrgQuery query) {

		// 数据权限
		// params.put(Constant.DATA_SCOPE, getDataScope("t1", "id"));

		// 机构列表
		List<SysOrgEntity> entityList = sysOrgMapper.getList(query);

		return TreeUtils.build(SysOrgConvert.INSTANCE.convertList(entityList));
	}

	/**
	 * 机构分页列表
	 * @param query 参数
	 * @return e
	 */
	@Override
	public PageResult<SysOrgVO> page(SysOrgQuery query) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		// 机构列表
		List<SysOrgEntity> list = sysOrgMapper.getList(query);
		PageInfo<SysOrgEntity> pageInfo = new PageInfo<>(list);
		return new PageResult<>(SysOrgConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
	}

	@Override
	public void save(SysOrgVO vo) {
		SysOrgEntity entity = SysOrgConvert.INSTANCE.convert(vo);
		sysOrgMapper.insertOrg(entity);
	}

	@Override
	public void update(SysOrgVO vo) {
		SysOrgEntity entity = SysOrgConvert.INSTANCE.convert(vo);

		// 上级机构不能为自身
		if(entity.getId().equals(entity.getParentId())){
			throw new ServerException("上级机构不能为自身");
		}
		// 上级机构不能为下级
		List<Long> subOrgList = getSubOrgIdList(entity.getId());
		if(subOrgList.contains(entity.getParentId())){
			throw new ServerException("上级机构不能为下级");
		}
		sysOrgMapper.updateById(entity);
	}

	@Override
	public void delete(Long id) {
		// 判断是否有子机构
		int orgCount = sysOrgMapper.countByParentId(id);
		if(orgCount > 0){
			throw new ServerException("请先删除子机构");
		}

		// 判断机构下面是否有用户
		long userCount = sysUserMapper.countByOrgId(id);
		if(userCount > 0){
			throw new ServerException("机构下面有用户，不能删除");
		}

		// 删除
		// removeById(id);
		SysOrgEntity params = new SysOrgEntity();
		params.setId(id);
		params.setDbStatus(0);
		sysOrgMapper.updateById(params);
	}

	@Override
	public List<Long> getSubOrgIdList(Long id) {
		// 所有机构的id、pid列表
		List<SysOrgEntity> orgList = sysOrgMapper.getIdAndPidList();

		// 递归查询所有子机构ID列表
		List<Long> subIdList = new ArrayList<>();
		getTree(id, orgList, subIdList);

		// 本机构也添加进去
		subIdList.add(id);

		return subIdList;
	}

	@Override
	public SysOrgEntity getById(Long id) {
		AssertUtils.isNull(id, "机构ID");
		return sysOrgMapper.getById(id);
	}

	/**
	 * 根据ID获取机构信息
	 * @param id 用户ID
	 * @param cache 为true则优先读取缓存
	 * @return 机构信息
	 */
	@Override
	public SysOrgEntity getById(Long id, Boolean cache) {
		AssertUtils.isNull(id, "机构ID");
		if(cache == null){
			cache = false;
		}
		String key = RedisKeys.getOrgCacheKey(id);
		if(!redisCache.hasKey(key)){
			SysOrgEntity org = sysOrgMapper.getById(id);
			if(org != null && org.getId() != null){
				redisCache.set(key, org, 7200);// 缓存2小时
			}
			return org;
		}else{
			if(cache){
				return JsonUtils.convertValue(redisCache.get(key), SysOrgEntity.class);
			}else{
				return sysOrgMapper.getById(id);
			}
		}
	}

	private void getTree(Long id, List<SysOrgEntity> orgList, List<Long> subIdList) {
		for(SysOrgEntity org : orgList){
			if (org.getParentId().equals(id)){
				getTree(org.getId(), orgList, subIdList);
				subIdList.add(org.getId());
			}
		}
	}
}
