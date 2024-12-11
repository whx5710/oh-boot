package com.iris.support.mapper;

import com.iris.core.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.support.entity.SysOrgEntity;
import com.iris.support.query.SysOrgQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构管理
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
@Ds(Constant.DYNAMIC_SYS_DB)
public interface SysOrgMapper {

    List<SysOrgEntity> getList(SysOrgQuery query);

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