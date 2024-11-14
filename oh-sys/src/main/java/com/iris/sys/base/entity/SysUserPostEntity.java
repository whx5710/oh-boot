package com.iris.sys.base.entity;

import com.iris.framework.entity.BaseEntity;

import java.util.Objects;

/**
 * 用户岗位关系
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class SysUserPostEntity extends BaseEntity {
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	* 岗位ID
	*/
	private Long postId;

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SysUserPostEntity that)) return false;
		return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getPostId(), that.getPostId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUserId(), getPostId());
	}
}