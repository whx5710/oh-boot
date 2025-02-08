package com.finn.support.service;

import com.finn.core.utils.PageResult;
import com.finn.support.entity.SysOrgEntity;
import com.finn.support.query.SysOrgQuery;
import com.finn.support.vo.SysOrgVO;

import java.util.List;

/**
 * 机构管理
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysOrgService {

	List<SysOrgVO> getList(SysOrgQuery query);

	PageResult<SysOrgVO> page(SysOrgQuery query);

	void save(SysOrgVO vo);

	void update(SysOrgVO vo);

	void delete(Long id);

	/**
	 * 根据机构ID，获取子机构ID列表(包含本机构ID)
	 * @param id   机构ID
	 */
	List<Long> getSubOrgIdList(Long id);

	SysOrgEntity getById(Long id);

	/**
	 * 根据ID获取机构信息
	 * @param id 用户ID
	 * @param cache 为true则优先读取缓存
	 * @return 机构信息
	 */
	SysOrgEntity getById(Long id, Boolean cache);
}