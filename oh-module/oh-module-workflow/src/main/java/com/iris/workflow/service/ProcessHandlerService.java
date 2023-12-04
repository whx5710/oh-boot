package com.iris.workflow.service;

import jakarta.annotation.Resource;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;


@Service
public class ProcessHandlerService {

    @Resource
    private RepositoryService repositoryService;

    /**
     * 部署流程
     * @param path
     * @param name
     * @return
     */
    public Deployment deploy(String path, String name){
        return repositoryService.createDeployment()
                .name(name) // 定义部署文件的名称
                .addClasspathResource(path) // 绑定需要部署的流程文件
                .deploy();// 部署流程
    }

    /**
     * 获取最新的流程
     * @param processKey
     * @return
     */
    public ProcessDefinition getProcessByKey(String processKey){
        return repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .latestVersion().singleResult();
    }

    /**
     * 根据部署的ID获取流程
     * @param deploymentId
     * @return
     */
    public ProcessDefinition getProcessByDeploymentId(String deploymentId){
        return repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
    }
}