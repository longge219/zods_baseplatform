package com.zods.workflow.modules.history.service.impl;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.common.bean.BeanUtils;
import com.zods.workflow.common.text.StringUtils;
import com.zods.workflow.modules.history.controller.dto.ActRuExecution;
import com.zods.workflow.modules.history.controller.dto.FlowInfo;
import com.zods.workflow.modules.history.controller.dto.TaskInfo;
import com.zods.workflow.modules.history.controller.dto.VariableInfo;
import com.zods.workflow.modules.history.controller.params.FlowHistoryParams;
import com.zods.workflow.modules.history.controller.params.HistoryParams;
import com.zods.workflow.modules.history.dao.ActRuExecutionMapper;
import com.zods.workflow.modules.history.service.FlowHistoryService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc 流程历史服务接口实现
 * @author jianglong
 * @date 2022-08-09
 **/
@Service
public class FlowHistoryServiceImpl implements FlowHistoryService {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    RepositoryService repositoryService;

    @Resource
    ActRuExecutionMapper actRuExecutionMapper;




    /**查询所有正在运行的流程实例列表*/
    public ResponseBean getListProcess(FlowHistoryParams flowHistoryParams){
        int start = (flowHistoryParams.getPageNumber() - 1) * flowHistoryParams.getPageSize();
        ProcessInstanceQuery condition = runtimeService.createProcessInstanceQuery();
        if (StringUtils.isNotEmpty(flowHistoryParams.getBussinesskey())) {
            condition.processInstanceBusinessKey(flowHistoryParams.getBussinesskey());
        }
        if (StringUtils.isNotEmpty(flowHistoryParams.getName())) {
            condition.processDefinitionName(flowHistoryParams.getName());
        }
        int total = condition.orderByProcessDefinitionId().desc().list().size();
        List<ProcessInstance> processList = condition.orderByProcessDefinitionId().desc().listPage(start, flowHistoryParams.getPageSize());
        List<FlowInfo> flows = new ArrayList<>();
        processList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getProcessInstanceId());
            info.setBusinessKey(p.getBusinessKey());
            info.setName(p.getProcessDefinitionName());
            info.setStartTime(p.getStartTime());
            info.setStartUserId(p.getStartUserId());
            info.setSuspended(p.isSuspended());
            info.setEnded(p.isEnded());
            // 查看当前活动任务
            List<Task> tasks =  taskService.createTaskQuery().processInstanceId(p.getProcessInstanceId()).list();
            String taskName = "";
            String assignee = "";
            for (Task t : tasks) {
                taskName += t.getName() + ",";
                assignee += t.getAssignee() + ",";
            }
            taskName = taskName.substring(0, taskName.length() -1);
            assignee = assignee.substring(0, assignee.length() -1);
            info.setCurrentTask(taskName);
            info.setAssignee(assignee);
            flows.add(info);
        });
        return ResponseBean.builder().data(flows).build();
    }

    /**查询所有流程实例列表-包含在运行和已结束*/
    public ResponseBean getListHistProcess(FlowHistoryParams flowHistoryParams){
        int start = (flowHistoryParams.getPageNumber() - 1) * flowHistoryParams.getPageSize();
        HistoricProcessInstanceQuery condition = historyService.createHistoricProcessInstanceQuery();
        if (StringUtils.isNotEmpty(flowHistoryParams.getBussinesskey())) {
            condition.processInstanceBusinessKey(flowHistoryParams.getBussinesskey());
        }
        if (StringUtils.isNotEmpty(flowHistoryParams.getName())) {
            condition.processDefinitionName(flowHistoryParams.getName());
        }
        int total = condition.orderByProcessInstanceStartTime().desc().list().size();
        List<HistoricProcessInstance> processList = condition.orderByProcessInstanceStartTime().desc().listPage(start, flowHistoryParams.getPageSize());
        List<FlowInfo> flows = new ArrayList<>();
        processList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getId());
            info.setBusinessKey(p.getBusinessKey());
            info.setName(p.getProcessDefinitionName());
            info.setStartTime(p.getStartTime());
            info.setEndTime(p.getEndTime());
            info.setStartUserId(p.getStartUserId());
            if (p.getEndTime() == null) {
                info.setEnded(false);
                // 查看当前活动任务
                List<Task> tasks =  taskService.createTaskQuery().processInstanceId(p.getId()).list();
                String taskName = "";
                String assignee = "";
                for (Task t : tasks) {
                    taskName += t.getName() + ",";
                    assignee += t.getAssignee() + ",";
                }
                taskName = taskName.substring(0, taskName.length() -1);
                assignee = assignee.substring(0, assignee.length() -1);
                info.setCurrentTask(taskName);
                info.setAssignee(assignee);
            } else {
                info.setEnded(true);
            }
            flows.add(info);
        });
        return ResponseBean.builder().data(flows).build();
    }

    /**查询一个流程的活动历史*/
   public  ResponseBean history(@RequestBody HistoryParams historyParams){
       int start = (historyParams.getPageNumber() - 1) * historyParams.getPageSize();
       List<HistoricActivityInstance> history = historyService.createHistoricActivityInstanceQuery().processInstanceId(historyParams.getProcessInstanceId())
               .activityType("userTask").orderByHistoricActivityInstanceStartTime().asc().listPage(start, historyParams.getPageSize());
       int total = historyService.createHistoricActivityInstanceQuery().processInstanceId(historyParams.getProcessInstanceId())
               .activityType("userTask").orderByHistoricActivityInstanceStartTime().asc().list().size();
       List<TaskInfo> infos  = new ArrayList<>();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       history.stream().forEach(h->{
           TaskInfo info = new TaskInfo();
           info.setProcessInstanceId(h.getProcessInstanceId());
           info.setStartTime(sdf.format(h.getStartTime()));
           if (h.getEndTime() != null) {
               info.setEndTime(sdf.format(h.getEndTime()));
           }
           info.setAssignee(h.getAssignee());
           info.setTaskName(h.getActivityName());
           List<Comment> comments = taskService.getTaskComments(h.getTaskId());
           if (comments.size() > 0) {
               info.setComment(comments.get(0).getFullMessage());
           }
           infos.add(info);
       });
       return ResponseBean.builder().data(infos).build();
    }

    /**查询所有正在运行的执行实例列表*/
    public List<FlowInfo> listExecutions(String name){
        List<ActRuExecution> executionList = actRuExecutionMapper.selectActRuExecutionListByProcessName(name);
        List<FlowInfo> flows = new ArrayList<>();
        executionList.stream().forEach(p -> {
            FlowInfo info = new FlowInfo();
            info.setProcessInstanceId(p.getProcInstId());
            if (p.getSuspensionState() == 1L) {
                info.setSuspended(false);
            } else {
                info.setSuspended(true);
            }
            if (p.getIsActive() == 0) {
                info.setActive(false);
            } else {
                info.setActive(true);
            }
            if (p.getActId() != null) {
                ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(p.getProcInstId()).singleResult();
                BpmnModel bpmnModel = repositoryService.getBpmnModel(process.getProcessDefinitionId());
                Map<String, FlowElement> nodes = bpmnModel.getMainProcess().getFlowElementMap();
                info.setCurrentTask(nodes.get(p.getActId()).getName());
                info.setName(process.getProcessDefinitionName());
            } else {
                ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(p.getProcInstId()).singleResult();
                info.setStartTime(process.getStartTime());
                info.setStartUserId(process.getStartUserId());
                info.setName(process.getProcessDefinitionName());
                List<Task> tasks =  taskService.createTaskQuery().processInstanceId(p.getProcInstId()).list();
                String taskName = "";
                for (Task t : tasks) {
                    taskName += t.getName() + ",";
                }
                taskName = taskName.substring(0, taskName.length() -1);
                info.setCurrentTask(taskName);
            }
            info.setStartTime(p.getStartTime());
            info.setExecutionId(p.getId());
            if (p.getParentId() == null) {
                info.setParentExecutionId("0");
            } else {
                info.setParentExecutionId(p.getParentId());
            }
            flows.add(info);
        });
        return flows;
    }


    /**查询一个流程的变量*/
    public ResponseBean variables(HistoryParams historyParams){
        int start = (historyParams.getPageNumber() - 1) * historyParams.getPageSize();
        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery().processInstanceId(historyParams.getProcessInstanceId())
                .orderByVariableName().asc().listPage(start, historyParams.getPageSize());
        int total = historyService.createHistoricVariableInstanceQuery().processInstanceId(historyParams.getProcessInstanceId()).orderByVariableName().asc().list().size();
        List<VariableInfo> infos = new ArrayList<>();
        variables.forEach(v->{
            VariableInfo info = new VariableInfo();
            BeanUtils.copyBeanProp(info, v);
            info.setValue(v.getValue().toString());
            infos.add(info);
        });
        return ResponseBean.builder().data(infos).build();
    }

}
