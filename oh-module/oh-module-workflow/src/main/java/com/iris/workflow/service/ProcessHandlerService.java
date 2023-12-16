package com.iris.workflow.service;

import org.camunda.bpm.engine.ParseException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import com.iris.framework.common.exception.ServerException;


@Service
public class ProcessHandlerService {

    private final RepositoryService repositoryService;

    public ProcessHandlerService(RepositoryService repositoryService){
        this.repositoryService = repositoryService;
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
        }catch (NullValueException e2){
            throw new ServerException("未找到流程文件，请检查是否存在！【" + path + "】");
        }
        return deployment;
    }

    /**
     * 根据流程定义文件路径和流程名称部署获取流程定义ID
     * @param path 流程定义文件路径
     * @param name 流程名称
     * @return 流程定义ID
     */
    public String getProcessDefIdByDeploy(String path, String name){
        return getProcessDefID(deploy(path, name));
    }

    /**
     * 获取流程定义ID
     * @param deployment 部署对象
     * @return 流程定义ID （ACT_RE_PROCDEF）
     */
    public String getProcessDefID(Deployment deployment){
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
}