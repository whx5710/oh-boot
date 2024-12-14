package com.iris.support.mapper;

import com.iris.core.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.framework.datasource.mapper.BaseMapper;
import com.iris.framework.datasource.service.IrisProvider;
import com.iris.support.entity.SysUserEntity;
import com.iris.support.query.SysRoleUserQuery;
import com.iris.support.query.SysUserQuery;
import org.apache.ibatis.annotations.InsertProvider;
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
@Ds(Constant.DYNAMIC_SYS_DB)
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

	List<SysUserEntity> getList(@Param("params") SysUserQuery params);

	SysUserEntity getById(@Param("id") Long id);

	List<SysUserEntity> getRoleUserList(SysRoleUserQuery params);

	SysUserEntity getByUsername(String username);

	SysUserEntity getByMobile(String mobile);
	// 保存用户
	int insertUser(SysUserEntity sysUserEntity);

	// 动态拼接SQL
	@InsertProvider(method = "insertEntity", type = IrisProvider.class)
	int insert(SysUserEntity entity);

	boolean updateById(SysUserEntity sysUserEntity);

	int countByOrgId(@Param("orgId")long orgId);
}