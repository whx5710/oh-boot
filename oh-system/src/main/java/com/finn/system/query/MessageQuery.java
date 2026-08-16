package com.finn.system.query;

import com.finn.framework.query.Query;

/**
* 系统消息查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-10-10
*/
public class MessageQuery extends Query {
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
     * 排除掉的状态 状态0未发送1未读2已读
     */
    private String noState;
    /**
     * 来源于-用户ID
     */
    private Long fromId;

    /**
     * 发送给-用户ID
     */
    private Long toId;

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

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getNoState() {
        return noState;
    }

    public void setNoState(String noState) {
        this.noState = noState;
    }
}