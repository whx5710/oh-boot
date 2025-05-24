package com.finn.support.mapper;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.framework.datasource.service.ModifyProviderService;
import com.finn.support.entity.DeptEntity;
import com.finn.support.query.DeptQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 部门管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
@Ds(Constant.DYNAMIC_SYS_DB)
public interface DeptMapper {

    List<DeptEntity> getList(DeptQuery query);

    /**
     * 获取所有部门的id、pid列表
     */
    @Select("select t1.id, t1.parent_id from sys_dept t1 where t1.db_status = 1")
    List<DeptEntity> getIdAndPidList();

    // 保存部门信息
    @InsertProvider(method = ModifyProviderService.INSERT, type = ModifyProviderService.class)
    int insertDept(DeptEntity deptEntity);

    @UpdateProvider(method = ModifyProviderService.UPDATE, type = ModifyProviderService.class)
    boolean updateById(DeptEntity deptEntity);

    @Select("select count(1) from sys_dept where db_status != 0 and parent_id = #{parentId}")
    int countByParentId(@Param("parentId") long parentId);

    @Select("select a.* from sys_dept a where a.id = #{id}")
    DeptEntity getById(@Param("id") Long id);
}