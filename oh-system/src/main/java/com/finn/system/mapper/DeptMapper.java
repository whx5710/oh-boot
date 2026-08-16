package com.finn.system.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.system.entity.DeptEntity;
import com.finn.system.query.DeptQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 部门管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface DeptMapper extends BaseMapper<DeptEntity> {

    List<DeptEntity> getList(DeptQuery query);

    @Select("select count(1) from sys_dept where db_status != 0 and parent_id = #{parentId}")
    int countByParentId(@Param("parentId") long parentId);

}