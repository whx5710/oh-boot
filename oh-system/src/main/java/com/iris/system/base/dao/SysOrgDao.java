package com.iris.system.base.dao;

import com.iris.system.base.entity.SysOrgEntity;
import com.iris.framework.datasource.dao.BaseDao;
import com.iris.system.base.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 机构管理
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface SysOrgDao {

    List<SysOrgEntity> getList(Map<String, Object> params);

    /**
     * 获取所有机构的id、pid列表
     */
    List<SysOrgEntity> getIdAndPidList();

    // 保存机构信息
    int insertOrg(SysOrgEntity sysOrgEntity);

    boolean updateById(SysOrgEntity sysOrgEntity);

    int countByParentId(@Param("parentId") long parentId);

    SysOrgEntity getById(Long id);
}