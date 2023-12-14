package com.iris.workflow.service;

import org.camunda.bpm.engine.ParseException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.bpmn.deployer.BpmnDeployer;
import org.camunda.bpm.engine.impl.util.StringUtil;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessApplicationDeploymentBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ResumePreviousBy;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.springframework.stereotype.Service;

import com.iris.framework.common.exception.ServerException;

import java.util.List;
import java.util.Optional;


@Service
public class ProcessHandlerService {

    private final RepositoryService repositoryService;
    private final SpringBootProcessApplication processApplication;

    public ProcessHandlerService(RepositoryService repositoryService, SpringBootProcessApplication processApplication){
        this.repositoryService = repositoryService;
        this.processApplication = processApplication;
    }
    /**
     * 部署流程
     * @param path 流程定义文件路径
     * @param name 流程名称
     * @return 部署对象
     */
    public Deployment deploy(String path, String name){
        Deployment deployment = null;
        try{
            deployment = repositoryService.createDeployment()
                        .name(name) // 定义部署文件的名称
                        .addClasspathResource(path) // 绑定需要部署的流程文件
                        .deploy();// 部署流程
        }catch(ParseException e1){
            throw new ServerException("流程定义格式异常，请检查！");
        }
        return deployment;
    }

    /**
     * 根据流程定义文件路径和流程名称部署获取流程定义ID
     * @param path 流程定义文件路径
     * @param name 流程名称
     * @return 流程定义ID
     */
    public String getProcDefIdByDeploy(String path, String name){
        return getProcDefID(deploy(path, name));
    }

    /**
     * 获取流程定义ID
     * @param deployment 部署对象
     * @return 流程定义ID （ACT_RE_PROCDEF）
     */
    public String getProcDefID(Deployment deployment){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        if(processDefinition == null){
            throw new ServerException("未找到流程定义对象，请检查是否已部署成功！");
        }
        return processDefinition.getId();
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

    /**
     * 以指定部署名部署，会生成最新版本的部署，并且激活旧版本的同名部署
     */
    public void deployProcessByName(String processArchiveName, String resourceName, BpmnModelInstance bpmnModelInstance) {
        getDeploymentByDefaultConfig(resourceName, bpmnModelInstance).name(processArchiveName).deploy();
    }
    /**
     * 在指定旧版部署id更新部署
     */
    public void deployProcessUpdateIncrementallyById(String oldDeploymentId, String resourceName, BpmnModelInstance bpmnModelInstance) {
        String processArchiveName = repositoryService.createDeploymentQuery().deploymentId(oldDeploymentId).singleResult().getName();
        getDeploymentByDefaultConfig(resourceName, bpmnModelInstance).name(processArchiveName).addDeploymentResources(oldDeploymentId).deploy();
    }
    /**
     * 在指定部署名更新部署，并指定需要COPY的其他部署的流程定义
     */
    public void deployProcessUpdateIncrementallyByName(String processArchiveName, String resourceName, BpmnModelInstance bpmnModelInstance) {
        Deployment deployment = Optional.ofNullable(repositoryService.createDeploymentQuery()
                        .deploymentName(processArchiveName).orderByDeploymentTime().desc().listPage(0, 1))//查找同名最新部署
                .map(e -> e.get(0)).orElseThrow(() -> new RuntimeException(processArchiveName + "not found"));
        List<String> oldResourceNames = repositoryService.getDeploymentResourceNames(deployment.getId());
        oldResourceNames.remove(resourceName);
        getDeploymentByDefaultConfig(resourceName, bpmnModelInstance).name(deployment.getName()).addDeploymentResourcesByName(deployment.getId(),oldResourceNames).deploy();
    }
    private ProcessApplicationDeploymentBuilder getDeploymentByDefaultConfig(String resourceName, BpmnModelInstance bpmnModelInstance) {
        resourceName = validateResourceName(resourceName);
        return repositoryService.createDeployment(processApplication.getReference())
                .addModelInstance(resourceName, bpmnModelInstance)
                //开启重复资源过滤 、关闭 isDeployChangedOnly
                .enableDuplicateFiltering(false)
                .resumePreviousVersions()
                .resumePreviousVersionsBy(ResumePreviousBy.RESUME_BY_PROCESS_DEFINITION_KEY);
    }
    /**
     * 如果不以.bpmn结尾则加上.bpmn后缀
     */
    private String validateResourceName(String resourceName) {
        if (!StringUtil.hasAnySuffix(resourceName, BpmnDeployer.BPMN_RESOURCE_SUFFIXES)) {
            resourceName = resourceName.concat(BpmnDeployer.BPMN_RESOURCE_SUFFIXES[1]);
        }
        return resourceName;
    }
}