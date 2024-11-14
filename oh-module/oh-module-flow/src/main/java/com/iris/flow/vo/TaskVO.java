package com.iris.flow.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;

/**
 *
 * 流程任务实体
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-03
 */
public class TaskVO {

    /**
     * 流程定义KEY
     * Process_demo20231222
     */
    @Schema(description = "流程定义KEY")
    private String proDefKey;
    /**
     * 流程定义ID
     * Process_demo20231222:13:0a229e0e-c03e-11ee-80d0-b48c9dca078f
     */
    @Schema(description = "流程定义ID")
    private String proDefId;
    /**
     * 流程实例ID
     *
     */
    @Schema(description = "流程实例ID")
    private String proInstId;
    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private String taskId;

    // 业务ID
    @Schema(description = "业务ID")
    private String businessKey;
    /**
     * 环节执行参数
     */
    @Schema(description = "环节执行参数")
    private HashMap<String, Object> params;

    public String getProDefKey() {
        return proDefKey;
    }

    public void setProDefKey(String proDefKey) {
        this.proDefKey = proDefKey;
    }

    public String getProDefId() {
        return proDefId;
    }

    public void setProDefId(String proDefId) {
        this.proDefId = proDefId;
    }

    public String getProInstId() {
        return proInstId;
    }

    public void setProInstId(String proInstId) {
        this.proInstId = proInstId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }
}
