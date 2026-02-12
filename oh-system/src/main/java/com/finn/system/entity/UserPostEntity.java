package com.finn.system.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;


/**
 * 用户岗位关系
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_user_post")
public class UserPostEntity extends BaseEntity {
	/**
	 * 用户ID
	 */
	@TableField("user_id")
	private Long userId;
	/**
	* 岗位ID
	*/
	@TableField("post_id")
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

}