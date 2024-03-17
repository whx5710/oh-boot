package com.iris.system.pim.dao;

import com.iris.system.pim.entity.SysOrgEntity;
import com.iris.framework.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 机构管理
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface SysOrgDao extends BaseDao<SysOrgEntity> {

    List<SysOrgEntity> getList(Map<String, Object> params);

    /**
     * 获取所有机构的id、pid列表
     */
    List<SysOrgEntity> getIdAndPidList();

}