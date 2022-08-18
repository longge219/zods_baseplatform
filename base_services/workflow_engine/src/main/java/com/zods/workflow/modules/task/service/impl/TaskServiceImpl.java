package com.zods.workflow.modules.task.service.impl;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.common.text.StringUtils;
import com.zods.workflow.modules.task.controller.dto.SysUser;
import com.zods.workflow.modules.task.controller.dto.TaskInfo;
import com.zods.workflow.modules.task.service.TaskService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import org.activiti.engine.FormService;
/**
 * @desc 任务管理服务接口实现
 * @author jianglong
 * @date 2022-08-09
 **/
public class TaskServiceImpl implements TaskService {


    @Autowired
    private org.activiti.engine.TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Resource
    private HistoryService historyService;

   // @Autowired
   // private  FormService formService;

    /**
     * 查询我的待办任务列表
     */
    public ResponseBean mylist(TaskInfo param) {
        SysUser user = getSysUser();
        String username = user.getLoginName();
        TaskQuery condition = taskService.createTaskQuery().taskAssignee(username);
        if (StringUtils.isNotEmpty(param.getTaskName())) {
            condition.taskName(param.getTaskName());
        }
        if (StringUtils.isNotEmpty(param.getProcessName())) {
            condition.processDefinitionName(param.getProcessName());
        }
        // 过滤掉流程挂起的待办任务
        int total = condition.active().orderByTaskCreateTime().desc().list().size();
        int start = (param.getPageNum()-1) * param.getPageSize();
        List<Task> taskList = condition.active().orderByTaskCreateTime().desc().listPage(start, param.getPageSize());
        List<TaskInfo> tasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskList.stream().forEach(a->{
            ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(a.getProcessInstanceId()).singleResult();
            TaskInfo info = new TaskInfo();
            info.setAssignee(a.getAssignee());
            info.setBusinessKey(process.getBusinessKey());
            info.setCreateTime(sdf.format(a.getCreateTime()));
            info.setTaskName(a.getName());
            info.setExecutionId(a.getExecutionId());
            info.setProcessInstanceId(a.getProcessInstanceId());
            info.setProcessName(process.getProcessDefinitionName());
            info.setStarter(process.getStartUserId());
            info.setStartTime(sdf.format(process.getStartTime()));
            info.setTaskId(a.getId());
           // String formKey = formService.getTaskFormData(a.getId()).getFormKey();
           // info.setFormKey(formKey);
            tasks.add(info);
        });
        return ResponseBean.builder().data(tasks).build();
    }

    /**
     * 查询所有待办任务列表
     */
    public ResponseBean alllist(TaskInfo param) {
        TaskQuery condition = taskService.createTaskQuery();
        if (StringUtils.isNotEmpty(param.getTaskName())) {
            condition.taskName(param.getTaskName());
        }
        if (StringUtils.isNotEmpty(param.getProcessName())) {
            condition.processDefinitionName(param.getProcessName());
        }
        int total = condition.active().orderByTaskCreateTime().desc().list().size();
        int start = (param.getPageNum()-1) * param.getPageSize();
        List<Task> taskList = condition.active().orderByTaskCreateTime().desc().listPage(start, param.getPageSize());
        List<TaskInfo> tasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskList.stream().forEach(a->{
            ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(a.getProcessInstanceId()).singleResult();
            TaskInfo info = new TaskInfo();
            info.setAssignee(a.getAssignee());
            info.setBusinessKey(process.getBusinessKey());
            info.setCreateTime(sdf.format(a.getCreateTime()));
            info.setTaskName(a.getName());
            info.setExecutionId(a.getExecutionId());
            info.setProcessInstanceId(a.getProcessInstanceId());
            info.setProcessName(process.getProcessDefinitionName());
            info.setStarter(process.getStartUserId());
            info.setStartTime(sdf.format(process.getStartTime()));
            info.setTaskId(a.getId());
            //String formKey = formService.getTaskFormData(a.getId()).getFormKey();
            //info.setFormKey(formKey);
            tasks.add(info);
        });
        return ResponseBean.builder().data(tasks).build();
    }

    /**
     * 用taskid查询formkey
     **/
    public ResponseBean alllist( String taskId)
    {
       // String formKey = formService.getTaskFormData(taskId).getFormKey();
        return ResponseBean.builder().data("formKey").build();
    }


    public ResponseBean completeTask( String taskId,  Map<String, Object> variables) {
        SysUser user = getSysUser();
        String username = user.getLoginName();
        taskService.setAssignee(taskId, username);
        // 查出流程实例id
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        if (variables == null) {
            taskService.complete(taskId);
        } else {
            // 添加审批意见
            if (variables.get("comment") != null) {
                taskService.addComment(taskId, processInstanceId, (String) variables.get("comment"));
                variables.remove("comment");
            }
            taskService.complete(taskId, variables);
        }
        return ResponseBean.builder().data("SUCCESS").build();
    }


    public List<TaskInfo> history( String taskId) {
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        List<HistoricActivityInstance> history = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask").orderByHistoricActivityInstanceStartTime().asc().list();
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
        return infos;
    }

    private  SysUser getSysUser() {
        return new SysUser();
    }

}
