package com.iris.system.base.mapper;

import com.iris.system.base.entity.SysUserEntity;
import com.iris.system.base.query.SysRoleUserQuery;
import com.iris.system.base.query.SysUserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface SysUserMapper {

	List<SysUserEntity> getList(@Param("params") SysUserQuery params);

	SysUserEntity getById(@Param("id") Long id);

	List<SysUserEntity> getRoleUserList(SysRoleUserQuery params);

	SysUserEntity getByUsername(String username);

	SysUserEntity getByMobile(String mobile);
	// 保存用户
	int insertUser(SysUserEntity sysUserEntity);

	boolean updateById(SysUserEntity sysUserEntity);

	int countByOrgId(@Param("orgId")long orgId);
}