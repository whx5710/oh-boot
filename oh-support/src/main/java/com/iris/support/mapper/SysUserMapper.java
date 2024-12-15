package com.iris.support.mapper;

import com.iris.core.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.framework.datasource.service.IrisProvider;
import com.iris.support.entity.SysUserEntity;
import com.iris.support.query.SysRoleUserQuery;
import com.iris.support.query.SysUserQuery;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * 系统用户
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
@Ds(Constant.DYNAMIC_SYS_DB)
public interface SysUserMapper {

	List<SysUserEntity> getList(@Param("params") SysUserQuery params);

	SysUserEntity getById(@Param("id") Long id);

	List<SysUserEntity> getRoleUserList(SysRoleUserQuery params);

	SysUserEntity getByUsername(String username);

	SysUserEntity getByMobile(String mobile);
	// 保存用户
	void insertUser(SysUserEntity sysUserEntity);

	// 动态拼接SQL
	@InsertProvider(method = "insertEntity", type = IrisProvider.class)
	int insert(SysUserEntity entity);

	// 动态拼接SQL
	@UpdateProvider(method = "updateById", type = IrisProvider.class)
	boolean updateById(SysUserEntity sysUserEntity);

	int countByOrgId(@Param("orgId")long orgId);
}