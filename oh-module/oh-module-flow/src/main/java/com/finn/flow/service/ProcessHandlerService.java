package com.finn.flow.service;

import com.finn.flow.entity.FlowEntity;
import com.finn.flow.utils.BpmnUtils;
import com.finn.flow.vo.FlowNodeVO;
import com.finn.core.exception.ServerException;
import org.camunda.bpm.engine.ParseException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.xml.instance.DomElement;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.List;

/**
 * 流程相关操作
 * 2023-12-27
 * @author 王小费 whx5710@qq.com
 */
@Service
public class ProcessHandlerService {

    private final RepositoryService repositoryService;

    private final FlowService flowService;

    private final FlowNodeService flowNodeService;

    public ProcessHandlerService(RepositoryService repositoryService, FlowService flowService, FlowNodeService flowNodeService){
        this.repositoryService = repositoryService;
        this.flowService = flowService;
        this.flowNodeService = flowNodeService;
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
     * 根据流程定义KEY部署流程
     * @param key
     * @return 流程定义ID
     */
    public String deployByKey(String key){
        FlowEntity flow = flowService.getByKey(key);
        if(flow == null){
            throw new ServerException("未找到自定义的流程，请检查");
        }
        String dbXml = flow.getXml();
        // svg图片
        String svgStr = flow.getSvgStr();
        Deployment deployment = null;
        String name = key.endsWith(".bpmn")?key:key + ".bpmn";
        String svgName = key + ".svg";
        try{
            deployment = repositoryService.createDeployment()
                    .name(name)                 // 定义部署文件的名称
                    .addString(name, dbXml)     // bpmn流程数据(ACT_GE_BYTEARRAY)  名称对应ACT_RE_PROCDEF.RESOURCE_NAME_
                    .addString(svgName, svgStr) // svg图片(ACT_GE_BYTEARRAY)      名称对应ACT_RE_PROCDEF.DGRM_RESOURCE_NAME_
                    .deploy();                  // 部署流程
        }catch(ParseException e1){
            throw new ServerException("流程定义格式异常，请检查！", e1.getMessage());
        }catch (NullValueException e2){
            throw new ServerException("未找到流程文件，请检查是否存在！", e2.getMessage());
        }
        // 保存环节
        String procDefId = getProcessDefID(deployment);
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(procDefId);
        List<DomElement> domElementList = bpmnModelInstance.getDocument().getRootElement().getChildElements();
        if(!ObjectUtils.isEmpty(domElementList)){
            DomElement domElement = domElementList.stream().filter(it -> "process".equals(it.getLocalName())).findFirst().orElse(null);
            List<FlowNodeVO> nodes = BpmnUtils.getNodeList(domElement);
            for(FlowNodeVO f: nodes){
                f.setProcDefId(procDefId);
                flowNodeService.save(f);
            }
        }
        return procDefId;
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
     * 获取部署列表，默认获取最近50次部署记录
     * @param processKey
     * @return
     */
    public List<ProcessDefinition> listProcessByKey(String processKey){
        return repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .orderByProcessDefinitionVersion()
                .desc()
                .listPage(0, 50);
    }

    /**
     * 获取部署对象
     * @param id
     * @return
     */
    public Deployment getDeployment(String id){
        return repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
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
     * 获取svg 图片
     * @param processKey
     * @return
     */
    public String processByKeySvg(String processKey) throws IOException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .latestVersion().singleResult();
        if(processDefinition == null){
            throw new ServerException("未找到流程定义对象!【" + processKey + "】");
        }
        InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),processDefinition.getDiagramResourceName());
        if(inputStream == null){
            throw new ServerException("该流程没有svg图片!【" + processKey + "】");
        }
        Reader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer stringBuffer = new StringBuffer();
        String svgStr = "";
        while ((svgStr = bufferedReader.readLine()) != null){
            stringBuffer.append(svgStr);
        }
        return stringBuffer.toString();
    }

    /**
     * 判断是否部署流程
     * @param processKey
     * @return 是否
     */
    public Boolean isPeploy(String processKey){
        return !ObjectUtils.isEmpty(repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).active().list());
    }


    /**
     * 测试方法
     * @param p
     */
    public void test(String p){

    }
}