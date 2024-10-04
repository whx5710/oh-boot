package com.iris.sys.base.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.sys.base.entity.SysOrgEntity;
import com.iris.sys.base.query.SysOrgQuery;
import com.iris.sys.base.vo.SysOrgVO;

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
}