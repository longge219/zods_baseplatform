package com.zods.workflow.modules.workflow.service.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.modules.workflow.controller.dto.ProcessDto;
import com.zods.workflow.modules.workflow.controller.param.WorkFlowParam;
import com.zods.workflow.modules.workflow.service.WorkFlowService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.activiti.validation.ValidationError;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipInputStream;
/**
 * @desc 流程管理服务接口实现
 * @author jianglong
 * @date 2022-08-22
 **/
@Service
@Slf4j
public class WorkFlowServiceIml implements WorkFlowService {

    @Resource
    RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    ProcessEngineConfiguration configuration;



    /**上传一个工作流文件*/
   public  void fileupload(String filename, InputStream is){
       if (filename.endsWith("zip")) {
           repositoryService.createDeployment().name(filename).addZipInputStream(new ZipInputStream(is)).deploy();
       } else if (filename.endsWith("bpmn") || filename.endsWith("xml")) {
           repositoryService.createDeployment().name(filename).addInputStream(filename, is).deploy();
       } else {
           log.error("文件格式错误");
       }
    }


    /**查询已部署工作流列表*/
    public ResponseBean getWorkFlowlist(WorkFlowParam workFlowParam){
        ProcessDefinitionQuery queryCondition = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotEmpty(workFlowParam.getKey())) {
            queryCondition.processDefinitionKey(workFlowParam.getKey());
        }
        if (StringUtils.isNotEmpty(workFlowParam.getName())) {
            queryCondition.processDefinitionName(workFlowParam.getName());
        }
        if (workFlowParam.isLatest()) {
            queryCondition.latestVersion();
        }
        int total = queryCondition.list().size();
        int start = (workFlowParam.getPageNumber() - 1) * workFlowParam.getPageSize();
        List<ProcessDefinition> pageList = queryCondition.orderByDeploymentId().desc().listPage(start, workFlowParam.getPageSize());
        List<ProcessDto> processDtolist = new ArrayList<ProcessDto>();
        for (int i = 0; i < pageList.size(); i++) {
            ProcessDto processDto = new ProcessDto();
            processDto.setDeploymentId(pageList.get(i).getDeploymentId());
            processDto.setId(pageList.get(i).getId());
            processDto.setKey(pageList.get(i).getKey());
            processDto.setName(pageList.get(i).getName());
            processDto.setResourceName(pageList.get(i).getResourceName());
            processDto.setDiagramresourceName(pageList.get(i).getDiagramResourceName());
            processDto.setVersion(pageList.get(i).getVersion());
            processDtolist.add(processDto);
        }
        return ResponseBean.builder().data(processDtolist).build();
    }

    /**删除一次部署的工作流*/
    public void removeWorkFlowByDeploymentId(String deploymentId){
        repositoryService.deleteDeployment(deploymentId, true);
    }

    /**查看工作流图片*/
    public void showresource (String pdid, HttpServletResponse response){
//        BpmnModel bpmnModel = repositoryService.getBpmnModel(pdid);
//        ProcessDiagramGenerator diagramGenerator = configuration.getProcessDiagramGenerator();
//        InputStream is = diagramGenerator.generateDiagram(bpmnModel, "png",  "宋体", "宋体", "宋体", configuration.getClassLoader(), 1.0);
//        ServletOutputStream output = response.getOutputStream();
//        IOUtils.copy(is, output);
    }

    /**查看工作流定义*/
    public void showProcessDefinition(String pdid, String resource, HttpServletResponse response){
        try{
            InputStream is = repositoryService.getResourceAsStream(pdid, resource);
            ServletOutputStream output = response.getOutputStream();
            IOUtils.copy(is, output);
        }catch(Exception e){
            log.error("看工作流定义出错");
            e.printStackTrace();
        }
    }


    /**将流程定义转为模型*/
    public String exchangeProcessToModel(@PathVariable("pdid") String pdid){
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(definition.getId());
        ObjectNode objectNode = new BpmnJsonConverter().convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        modelData.setKey(definition.getKey());
        modelData.setName(definition.getName());
        modelData.setCategory(definition.getCategory());
        ObjectNode modelJson = new ObjectMapper().createObjectNode();
        modelJson.put(ModelDataJsonConstants.MODEL_NAME, definition.getName());
        modelJson.put(ModelDataJsonConstants.MODEL_DESCRIPTION, definition.getDescription());
        List<Model> models = repositoryService.createModelQuery().modelKey(definition.getKey()).list();
        if (models.size() > 0) {
            Integer version = models.get(0).getVersion();
            version++;
            modelJson.put(ModelDataJsonConstants.MODEL_REVISION, version);
            // 删除旧模型
            repositoryService.deleteModel(models.get(0).getId());
            modelData.setVersion(version);
        } else {
            modelJson.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        }
        modelData.setMetaInfo(modelJson.toString());
        modelData.setDeploymentId(definition.getDeploymentId());
        // 保存新模型
        repositoryService.saveModel(modelData);
        // 保存模型json
        repositoryService.addModelEditorSource(modelData.getId(), objectNode.toString().getBytes(StandardCharsets.UTF_8));
        return objectNode.toString();
    }


    /**遍历流程信息*/
    public ResponseBean removeProcess(String processInstanceId){
        String processDefinitionId = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof UserTask) {
                UserTask userTask = (UserTask) flowElement;
                System.out.println(flowElement.getName());
                System.out.println(flowElement.getId());
                System.out.println(userTask.getAssignee());
                String assigneeEl = userTask.getAssignee();
                if (StringUtils.isBlank(assigneeEl)) {
                    continue;
                }
                if (assigneeEl.startsWith("${") && assigneeEl.endsWith("}") && assigneeEl.length() > 3) {
                    String assignee = assigneeEl.substring(2, assigneeEl.length() - 2);
                    System.out.println("assignee:" + assignee);
                }
            }
        }
        return ResponseBean.builder().data(flowElements).build();
    }

    /**撤销:强制结束一个流程*/
    public ResponseBean forceEnd(@PathVariable String taskId){
        Task t = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processDefinitionId = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult().getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        // 寻找流程实例当前任务的activeId
        Execution execution = runtimeService.createExecutionQuery().executionId(t.getExecutionId()).singleResult();
        String activityId = execution.getActivityId();
        FlowNode currentNode = (FlowNode)bpmnModel.getMainProcess().getFlowElement(activityId);
        // 创建结束节点和连接线
        EndEvent end = new EndEvent();
        end.setName("强制结束");
        end.setId("forceEnd");
        List<SequenceFlow> newSequenceFlowList = new ArrayList<SequenceFlow>();
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newFlow");
        newSequenceFlow.setSourceFlowElement(currentNode);
        newSequenceFlow.setTargetFlowElement(end);
        newSequenceFlowList.add(newSequenceFlow);
        // 备份原有方向
        List<SequenceFlow> dataflows = currentNode.getOutgoingFlows();
        List<SequenceFlow> oriSequenceFlows = new ArrayList<SequenceFlow>();
        oriSequenceFlows.addAll(dataflows);
        // 清空原有方向
        currentNode.getOutgoingFlows().clear();
        // 设置新方向
        currentNode.setOutgoingFlows(newSequenceFlowList);
        // 完成当前任务
        taskService.addComment(taskId, t.getProcessInstanceId(), "comment", "撤销流程");
        taskService.complete(taskId);
        // 恢复原有方向
        currentNode.setOutgoingFlows(oriSequenceFlows);
        return ResponseBean.builder().data("SUCCESS").build();
    }


    /**驳回或跳转到指定节点*/
    public ResponseBean jump( String taskId,  String sid){
        Task t = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processDefinitionId = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult().getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        // 寻找流程实例当前任务的activeId
        Execution execution = runtimeService.createExecutionQuery().executionId(t.getExecutionId()).singleResult();
        String activityId = execution.getActivityId();
        FlowNode currentNode = (FlowNode)bpmnModel.getMainProcess().getFlowElement(activityId);
        FlowNode targetNode = (FlowNode)bpmnModel.getMainProcess().getFlowElement(sid);
        // 创建连接线
        List<SequenceFlow> newSequenceFlowList = new ArrayList<SequenceFlow>();
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newFlow");
        newSequenceFlow.setSourceFlowElement(currentNode);
        newSequenceFlow.setTargetFlowElement(targetNode);
        newSequenceFlowList.add(newSequenceFlow);
        // 备份原有方向
        List<SequenceFlow> dataflows = currentNode.getOutgoingFlows();
        List<SequenceFlow> oriSequenceFlows = new ArrayList<SequenceFlow>();
        oriSequenceFlows.addAll(dataflows);
        // 清空原有方向
        currentNode.getOutgoingFlows().clear();
        // 设置新方向
        currentNode.setOutgoingFlows(newSequenceFlowList);
        // 完成当前任务
        taskService.addComment(taskId, t.getProcessInstanceId(), "comment", "跳转节点");
        taskService.complete(taskId);
        // 恢复原有方向
        currentNode.setOutgoingFlows(oriSequenceFlows);
        return ResponseBean.builder().data("SUCCESS").build();
    }


    /**动态创建流程*/
    public ResponseBean createProcess(){
        // 开始节点的属性
        StartEvent startEvent=new StartEvent();
        startEvent.setId("start");
        startEvent.setName("start");
        // 普通UserTask节点
        UserTask userTask=new UserTask();
        userTask.setId("userTask");
        userTask.setName("审批任务");
        // 结束节点属性
        EndEvent endEvent=new EndEvent();
        endEvent.setId("end");
        endEvent.setName("end");
        // 连线信息
        List<SequenceFlow> flows=new ArrayList<SequenceFlow>();
        List<SequenceFlow> toEnd=new ArrayList<SequenceFlow>();
        SequenceFlow s1=new SequenceFlow();
        s1.setId("flow1");
        s1.setName("flow1");
        s1.setSourceRef(startEvent.getId());
        s1.setTargetRef(userTask.getId());
        flows.add(s1);

        SequenceFlow s2=new SequenceFlow();
        s2.setId("flow2");
        s2.setName("flow2");
        s2.setSourceRef(userTask.getId());
        s2.setTargetRef(endEvent.getId());
        toEnd.add(s2);
        startEvent.setOutgoingFlows(flows);
        userTask.setOutgoingFlows(toEnd);

        // 给流程对象添加元素
        org.activiti.bpmn.model.Process process=new Process();
        process.setId("dynamicProcess");
        process.setName("动态流程");
        process.addFlowElement(startEvent);
        process.addFlowElement(s1);
        process.addFlowElement(userTask);
        process.addFlowElement(s2);
        process.addFlowElement(endEvent);
        // 创建模型对象
        BpmnModel bpmnModel=new BpmnModel();
        bpmnModel.addProcess(process);
        // 流程图自动布局
        new BpmnAutoLayout(bpmnModel).execute();

        // 模型合法性校验
        List<ValidationError> validationErrorList = repositoryService.validateProcess(bpmnModel);
        if (validationErrorList.size() == 0) {
            // 模型合法就部署流程
            Deployment deploy = repositoryService.createDeployment().category("dynamic")
                    .key("dynamicProcess")
                    .addBpmnModel("dynamicProcess.bpmn20.xml", bpmnModel)
                    .deploy();
            return ResponseBean.builder().data("SUCCESS").build();
        } else {
            return ResponseBean.builder().data("FAIL").build();
        }
    }
}
