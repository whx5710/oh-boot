package com.iris.system.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iris.framework.common.entity.BaseEntity;


/**
 * 系统消息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-10-10
 */
@TableName("sys_message")
public class SysMessageEntity extends BaseEntity {
	/**
	* 标题
	*/
	private String title;

	/**
	* 内容
	*/
	private String content;

	/**
	 * 消息类别success普通消息warning警告error错误
	*/
	private String type;

	/**
	* 状态0未发送1未读2已读
	*/
	private String state;

	/**
	* 来源于-用户ID
	*/
	private Long fromId;

	/**
	 * 发送人
	 */
	private String fromName;

	/**
	* 发送给-用户ID
	*/
	private Long toId;

	/**
	 * 接收人
	 */
	private String toName;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}
}