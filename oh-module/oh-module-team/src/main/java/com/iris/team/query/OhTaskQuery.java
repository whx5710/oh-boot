package com.iris.team.query;

import com.iris.framework.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
* 任务表查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Schema(description = "任务表查询")
public class OhTaskQuery extends Query {
    // 查询关键字
    String keyWord;
    // 1任务2需求3设计4缺陷9其他
    String [] taskType;
    // 状态（1待办项2进行中3已完成）
    String status;
    String startTime;
    String endTime;
    // 项目ID
    Long projectId;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String[] getTaskType() {
        return taskType;
    }

    public void setTaskType(String[] taskType) {
        this.taskType = taskType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}