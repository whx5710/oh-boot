package com.finn.support.mapper;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.framework.datasource.service.ModifyProviderService;
import com.finn.support.entity.OrgEntity;
import com.finn.support.query.OrgQuery;
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
public interface OrgMapper {

    List<OrgEntity> getList(OrgQuery query);

    /**
     * 获取所有机构的id、pid列表
     */
    @Select("select t1.id, t1.parent_id from sys_org t1 where t1.db_status = 1")
    List<OrgEntity> getIdAndPidList();

    // 保存机构信息
    @InsertProvider(method = ModifyProviderService.INSERT, type = ModifyProviderService.class)
    int insertOrg(OrgEntity orgEntity);

    @UpdateProvider(method = ModifyProviderService.UPDATE, type = ModifyProviderService.class)
    boolean updateById(OrgEntity orgEntity);

    @Select("select count(1) from sys_org where db_status != 0 and parent_id = #{parentId}")
    int countByParentId(@Param("parentId") long parentId);

    @Select("select a.* from sys_org a where a.id = #{id}")
    OrgEntity getById(@Param("id") Long id);
}