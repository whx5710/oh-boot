package com.finn.support.service;

import com.finn.core.utils.PageResult;
import com.finn.support.entity.OrgEntity;
import com.finn.support.query.OrgQuery;
import com.finn.support.vo.OrgVO;

import java.util.List;

/**
 * 机构管理
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
public interface OrgService {

	List<OrgVO> getList(OrgQuery query);

	PageResult<OrgVO> page(OrgQuery query);

	void save(OrgVO vo);

	void update(OrgVO vo);

	void delete(Long id);

	/**
	 * 根据机构ID，获取子机构ID列表(包含本机构ID)
	 * @param id   机构ID
	 */
	List<Long> getSubOrgIdList(Long id);

	OrgEntity getById(Long id);

	/**
	 * 根据ID获取机构信息
	 * @param id 用户ID
	 * @param cache 为true则优先读取缓存
	 * @return 机构信息
	 */
	OrgEntity getById(Long id, Boolean cache);
}