package com.iris.sys.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.TreeUtils;
import com.iris.sys.base.mapper.SysOrgMapper;
import com.iris.sys.base.mapper.SysUserMapper;
import com.iris.sys.base.query.SysOrgQuery;
import com.iris.sys.base.vo.SysOrgVO;
import com.iris.sys.base.convert.SysOrgConvert;
import com.iris.core.exception.ServerException;
import com.iris.sys.base.entity.SysOrgEntity;
import com.iris.sys.base.service.SysOrgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public SysOrgServiceImpl(SysUserMapper sysUserMapper, SysOrgMapper sysOrgMapper) {
		this.sysUserMapper = sysUserMapper;
		this.sysOrgMapper = sysOrgMapper;
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
	@Transactional(rollbackFor = Exception.class)
	public void save(SysOrgVO vo) {
		SysOrgEntity entity = SysOrgConvert.INSTANCE.convert(vo);
		sysOrgMapper.insertOrg(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
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
	@Transactional(rollbackFor = Exception.class)
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
		return sysOrgMapper.getById(id);
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
