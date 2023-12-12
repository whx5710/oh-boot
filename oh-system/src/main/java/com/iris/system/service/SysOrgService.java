package com.iris.system.service;

import com.iris.system.entity.SysOrgEntity;
import com.iris.system.vo.SysOrgVO;
import com.iris.framework.mybatis.service.BaseService;

import java.util.List;

/**
 * 机构管理
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysOrgService extends BaseService<SysOrgEntity> {

	List<SysOrgVO> getList();

	void save(SysOrgVO vo);

	void update(SysOrgVO vo);

	void delete(Long id);

	/**
	 * 根据机构ID，获取子机构ID列表(包含本机构ID)
	 * @param id   机构ID
	 */
	List<Long> getSubOrgIdList(Long id);
}