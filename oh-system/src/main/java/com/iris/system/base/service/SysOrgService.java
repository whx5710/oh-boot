package com.iris.system.base.service;

import com.iris.system.base.entity.SysOrgEntity;
import com.iris.system.base.vo.SysOrgVO;

import java.util.List;

/**
 * 机构管理
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysOrgService {

	List<SysOrgVO> getList();

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