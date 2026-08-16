package com.finn.system.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.system.entity.RoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenuEntity> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	@Select("select menu_id from sys_role_menu where role_id = #{roleId} and db_status = 1")
	List<Long> getMenuIdList(@Param("roleId") Long roleId);

}
