package com.iris.support.mapper;

import com.iris.core.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.framework.datasource.service.ProviderService;
import com.iris.support.entity.SysOrgEntity;
import com.iris.support.query.SysOrgQuery;
import org.apache.ibatis.annotations.*;

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
    @Select("select t1.id, t1.parent_id from sys_org t1 where t1.db_status = 1")
    List<SysOrgEntity> getIdAndPidList();

    // 保存机构信息
    @InsertProvider(method = ProviderService.INSERT, type = ProviderService.class)
    int insertOrg(SysOrgEntity sysOrgEntity);

    @UpdateProvider(method = ProviderService.UPDATE, type = ProviderService.class)
    boolean updateById(SysOrgEntity sysOrgEntity);

    @Select("select count(1) from sys_org where db_status != 0 and parent_id = #{parentId}")
    int countByParentId(@Param("parentId") long parentId);

    @Select("select a.*,b.name parentName from sys_org a left join sys_org b on a.parent_id = b.id where a.id = #{id}")
    SysOrgEntity getById(@Param("id") Long id);
}