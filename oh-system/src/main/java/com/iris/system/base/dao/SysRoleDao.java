package com.iris.system.base.dao;

import com.iris.system.base.entity.SysRoleEntity;
import com.iris.system.base.query.SysRoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色管理
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface SysRoleDao {

    int insertRole(SysRoleEntity sysRoleEntity);

    /**
     * 根据用户ID，获取用户最大的数据范围
     */
    Integer getDataScopeByUserId(@Param("userId") Long userId);

    List<SysRoleEntity> getList(SysRoleQuery sysRoleQuery);

    boolean updateById(SysRoleEntity sysRoleEntity);

    SysRoleEntity getById(long id);

}
