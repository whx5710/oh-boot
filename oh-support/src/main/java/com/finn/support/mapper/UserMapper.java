package com.finn.support.mapper;

import com.finn.framework.datasource.annotations.Pages;
import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.support.entity.UserEntity;
import com.finn.support.query.RoleUserQuery;
import com.finn.support.query.UserQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 系统用户
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

	List<UserEntity> getList(@Param("params") UserQuery params);

	@Select("select t1.*, (select t2.name from sys_dept t2 where t2.id = t1.dept_id) deptName from sys_user t1 where t1.db_status = 1 and t1.id = #{id}")
	UserEntity getById(@Param("id") Long id);

	@Pages
	List<UserEntity> getRoleUserList(RoleUserQuery params);

	@Select("select a.*,b.name as dept_name from sys_user a left join sys_dept b on a.dept_id = b.id where a.db_status != 0 and a.username = #{username}")
	UserEntity getByUsername(@Param("username") String username);

	@Select("select a.*,b.name as dept_name from sys_user a left join sys_dept b on a.dept_id = b.id where a.db_status != 0 and a.mobile = #{mobile}")
	UserEntity getByMobile(@Param("mobile") String mobile);

	// 保存用户
//	void insertUser(UserEntity userEntity);


	// 根据ID修改用户信息
//	boolean updateById(UserEntity userEntity);

	// 解绑租户用户
	boolean unbindUser(UserEntity userEntity);

	@Select("select count(1) from sys_user where db_status != 0 and dept_id = #{deptId}")
	int countByDeptId(@Param("deptId")long deptId);
}