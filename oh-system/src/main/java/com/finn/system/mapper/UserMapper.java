package com.finn.system.mapper;

import com.finn.framework.aop.annotations.Pages;
import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.system.entity.UserEntity;
import com.finn.system.query.RoleUserQuery;
import com.finn.system.query.UserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

	@Pages
	List<UserEntity> pageByRole(@Param("params") UserQuery params);

	@Select("select t1.*, (select t2.name from sys_dept t2 where t2.id = t1.dept_id) deptName from sys_user t1 where t1.db_status = 1 and t1.id = #{id}")
	UserEntity getById(@Param("id") Long id);

	@Pages
	List<UserEntity> getRoleUserList(RoleUserQuery params);

	/**
	 * 根据用户名和用户类型查询用户信息
	 * @param username
	 * @return
	 */
	UserEntity getByUsername(@Param("username") String username);

	/**
	 *
	 * @param mobile
	 * @return
	 */
	UserEntity getByMobile(@Param("mobile") String mobile);

}