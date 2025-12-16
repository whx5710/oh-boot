package com.finn.support.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.TenantEntity;
import com.finn.support.enums.DataScopeEnum;

/**
 * 角色
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
@TableName("sys_role")
public class RoleEntity extends TenantEntity {
	/**
	 * 角色名称
	 */
	private String name;
    /**
     * 角色编码
     */
    private String code;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 数据范围  {@link DataScopeEnum}
	 */
	@TableField("data_scope")
	private Integer dataScope;
	/**
	 * 系统内置
	 */
	@TableField("is_system")
	private Integer isSystem;

	/**
	 * 租户
	 */
	@TableField(exists = false)
	private String tenantName;

	@TableField(exists = false)
	private String menuIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDataScope() {
		return dataScope;
	}

	public void setDataScope(Integer dataScope) {
		this.dataScope = dataScope;
	}

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
}
