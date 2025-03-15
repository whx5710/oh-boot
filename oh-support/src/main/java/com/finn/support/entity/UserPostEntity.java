package com.finn.support.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.entity.BaseEntity;


/**
 * 用户岗位关系
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class UserPostEntity extends BaseEntity {
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	* 岗位ID
	*/
	private Long postId;

	@TableField(exists = false)
	private String tenantId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	@Override
	public String getTenantId() {
		return tenantId;
	}

	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}