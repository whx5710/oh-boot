package com.finn.team.entity;

import com.finn.framework.entity.BaseEntity;

/**
 * 任务人员表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
public class OhTaskUserEntity extends BaseEntity {
	/**
	* 任务ID
	*/
	private Long taskId;

	/**
	* 人员ID
	*/
	private Long userId;

	/**
	* 用户昵称
	*/
	private String nickName;

	/**
	* 人员类型1负责人2协作人
	*/
	private String personType;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}
}