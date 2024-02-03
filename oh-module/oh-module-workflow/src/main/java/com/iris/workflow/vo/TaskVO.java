package com.iris.workflow.vo;

import java.util.HashMap;

/**
 *
 * 流程任务实体
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-03
 */
public class TaskVO {
    /**
     * 流程定义ID
     * Process_demo20231222:13:0a229e0e-c03e-11ee-80d0-b48c9dca078f
     */
    private String procDefId;
    /**
     * 流程实例ID
     *
     */
    private String procInstId;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 环节执行参数
     */
    private HashMap<String, Object> params;

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }
}
