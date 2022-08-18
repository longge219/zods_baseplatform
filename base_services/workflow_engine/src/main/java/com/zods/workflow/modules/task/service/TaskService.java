package com.zods.workflow.modules.task.service;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.modules.task.controller.dto.TaskInfo;
import java.util.List;
import java.util.Map;
/**
 * @desc 任务管理服务接口
 * @author jianglong
 * @date 2022-08-09
 **/
public interface TaskService {


    /**查询我的待办任务列表*/
    ResponseBean mylist(TaskInfo param);

    /**查询所有待办任务列表*/
    ResponseBean alllist(TaskInfo param);

    /**用taskid查询formkey*/
    ResponseBean alllist( String taskId);


    ResponseBean completeTask(String taskId,Map<String, Object> variables);

    List<TaskInfo> history( String taskId);

}
